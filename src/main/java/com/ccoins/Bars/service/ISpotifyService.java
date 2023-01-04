package com.ccoins.bars.service;

import com.ccoins.bars.spotify.CredentialsSPTFDTO;
import com.ccoins.bars.spotify.PlaylistSPTF;
import com.ccoins.bars.spotify.RecentlyPlayedSPTF;
import com.ccoins.bars.spotify.UriSPTF;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface ISpotifyService {

    ResponseEntity<PlaylistSPTF> getPlaylist(HttpHeaders headers);

    ResponseEntity<RecentlyPlayedSPTF> getRecentlyPlayed(HttpHeaders headers, Integer limit);

    void addTrackToQueue(HttpHeaders headers, UriSPTF trackUri);

    CredentialsSPTFDTO getCredentials();
}
