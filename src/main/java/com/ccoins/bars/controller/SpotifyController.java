package com.ccoins.bars.controller;

import com.ccoins.bars.controller.swagger.ISpotifyController;
import com.ccoins.bars.service.ISpotifyService;
import com.ccoins.bars.spotify.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/spotify")
public class SpotifyController implements ISpotifyController {

    @Autowired
    private ISpotifyService service;

    @GetMapping("/playlist")
    @Override
    public ResponseEntity<PlaylistSPTF> getPlaylist(@RequestHeader HttpHeaders headers){

        return this.service.getPlaylist(headers);
    }

    @GetMapping("/recently-played/{limit}")
    @Override
    public ResponseEntity<RecentlyPlayedSPTF> getRecentlyPlayed(@RequestHeader HttpHeaders headers, @PathVariable Integer limit){

        return this.service.getRecentlyPlayed(headers, limit);
    }

    @PostMapping("/playlist")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public void addTrackToPlaylist(@RequestHeader HttpHeaders headers, @RequestBody UriSPTF request){
        this.service.addTrackToQueue(headers, request);
    }

    @GetMapping("/config")
    @Override
    public ResponseEntity<CredentialsSPTFDTO> getCredentials(){
        return ResponseEntity.ok(this.service.getCredentials());
    }

    @GetMapping("/playback")
    @Override
    public ResponseEntity<Optional<PlaybackSPTF>> getPlaybackState(@RequestHeader HttpHeaders headers){
        return ResponseEntity.ok(this.service.getPlaybackState(headers));
    }

    @PostMapping("/actualSongs")
    @Override
    public void addBarTokenToActualSongs(@RequestBody @Valid BarTokenDTO request){
        this.service.addBarTokenInMemory(request.getId(), request.getToken());
    }
}
