package com.ccoins.bars.service.impl;

import com.ccoins.bars.configuration.CredentialsSPTFConfig;
import com.ccoins.bars.feign.SpotifyFeign;
import com.ccoins.bars.service.ISpotifyService;
import com.ccoins.bars.spotify.CredentialsSPTFDTO;
import com.ccoins.bars.spotify.PlaylistSPTF;
import com.ccoins.bars.spotify.RecentlyPlayedSPTF;
import com.ccoins.bars.spotify.UriSPTF;
import com.ccoins.bars.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpotifyService implements ISpotifyService {

    private final SpotifyFeign feign;

    private final CredentialsSPTFConfig credentials;

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

    private void setParameters(HttpHeaders headers){
        headers.set("Accept-Encoding", "identity");
        headers.remove("content-length");
    }
}
