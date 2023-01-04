package com.ccoins.bars.feign;

import com.ccoins.bars.spotify.PlaybackSPTF;
import com.ccoins.bars.spotify.PlaylistSPTF;
import com.ccoins.bars.spotify.RecentlyPlayedSPTF;
import com.ccoins.bars.spotify.UriSPTF;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "${spotify.feign.name}", url = "${spotify.feign.url}")
@RequestMapping("${spotify.feign.req-map}")
public interface SpotifyFeign {

    @GetMapping(value = "/queue", produces = "application/json", consumes = "application/json")
    PlaylistSPTF getQueue(@RequestHeader HttpHeaders headers);

    @GetMapping(value = "/recently-played?limit={limit}", produces = "application/json", consumes = "application/json")
    RecentlyPlayedSPTF getRecentlyPlayed(@RequestHeader HttpHeaders headers, @PathVariable Integer limit);

    @PostMapping(value = "/queue", produces = "application/json", consumes = "application/json")
    void addTrackToQueue(@RequestHeader HttpHeaders headers, @RequestBody UriSPTF trackUri);

    @GetMapping(produces = "application/json", consumes = "application/json")
    Optional<PlaybackSPTF> getPlayState(@RequestHeader HttpHeaders headers);

}
