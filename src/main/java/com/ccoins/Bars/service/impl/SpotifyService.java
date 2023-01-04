package com.ccoins.bars.service.impl;

import com.ccoins.bars.configuration.CredentialsSPTFConfig;
import com.ccoins.bars.exceptions.UnauthorizedException;
import com.ccoins.bars.exceptions.constant.ExceptionConstant;
import com.ccoins.bars.feign.SpotifyFeign;
import com.ccoins.bars.service.ISpotifyService;
import com.ccoins.bars.spotify.*;
import com.ccoins.bars.utils.HttpHeadersUtils;
import com.ccoins.bars.utils.MapperUtils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.util.CopyOnWriteLinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SpotifyService implements ISpotifyService {

    private final SpotifyFeign feign;
    private final CredentialsSPTFConfig credentials;

    protected CopyOnWriteLinkedHashMap<BarTokenSPTF, PlaybackSPTF> actualSongs = new CopyOnWriteLinkedHashMap<>();


    @Autowired
    public SpotifyService(SpotifyFeign feign, CredentialsSPTFConfig credentials) {
        this.feign = feign;
        this.credentials = credentials;
    }

    @Override
    public ResponseEntity<PlaylistSPTF> getPlaylist(HttpHeaders headers) {

        this.setParameters(headers);
        PlaylistSPTF response =  this.feign.getQueue(headers);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RecentlyPlayedSPTF> getRecentlyPlayed(HttpHeaders headers, Integer limit) {

        this.setParameters(headers);
        RecentlyPlayedSPTF response =  this.feign.getRecentlyPlayed(headers,limit);
        return ResponseEntity.ok(response);
    }

    @Override
    public void addTrackToQueue(HttpHeaders headers, UriSPTF trackUri) {

        this.setParameters(headers);
        this.feign.addTrackToQueue(headers,trackUri);
    }

    @Override
    public CredentialsSPTFDTO getCredentials(){

        return (CredentialsSPTFDTO) MapperUtils.map(this.credentials, CredentialsSPTFDTO.class);
    }

    @Override
    public Optional<PlaybackSPTF> getPlaybackState(HttpHeaders headers){
        this.setParameters(headers);
        return this.feign.getPlayState(headers);
    }

    private void setParameters(HttpHeaders headers){
        headers.set("Accept-Encoding", "identity");
        headers.remove("content-length");
    }

    @Scheduled(fixedDelayString = "${spotify.playback.cron}")
    public void loadActualSongs(){ //actualiza el estado de las canciones de los bares cada X tiempo
        actualSongs.forEach((key,value) -> {
            try {
                this.addActualSongToList(key);
            }catch(Exception ignored){

            }
        });
    }

    public void addActualSongToList(BarTokenSPTF key){
        HttpHeaders headers = HttpHeadersUtils.getHeaderFromToken(key.getToken());
        this.setParameters(headers);
        Optional<PlaybackSPTF> optional;

        optional = this.feign.getPlayState(headers);

        if(optional.isPresent())
            this.actualSongs.put(key, optional.get());
        else
            this.actualSongs.replace(key, null);
    }

    @Override
    public void addBarTokenInMemory(Long barId, String token){

        BarTokenSPTF barToken = BarTokenSPTF.builder().id(barId).token(token).build();

        try {
            if (!this.actualSongs.containsKey(barToken))
                this.addActualSongToList(barToken);
        }catch(FeignException e){
            throw new UnauthorizedException(ExceptionConstant.SPOTIFY_PLAYBACK_ERROR_CODE,
                    this.getClass(),
                    e.getLocalizedMessage());
        }
    }
}
