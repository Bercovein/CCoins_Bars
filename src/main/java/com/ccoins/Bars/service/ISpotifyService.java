package com.ccoins.bars.service;

import com.ccoins.bars.spotify.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ISpotifyService {

    ResponseEntity<PlaylistSPTF> getPlaylist(HttpHeaders headers);

    ResponseEntity<RecentlyPlayedSPTF> getRecentlyPlayed(HttpHeaders headers, Integer limit);

    void addTrackToQueue(HttpHeaders headers, UriSPTF trackUri);

    CredentialsSPTFDTO getCredentials();

    Optional<PlaybackSPTF> getPlaybackState(HttpHeaders headers);

    void addBarTokenInMemory(Long barId, String token);
}
