package com.example.sona;

import android.util.Log;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.youtube.YoutubeService;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.StreamInfo;
import org.schabi.newpipe.extractor.stream.VideoStream;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.ListExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JavaFunctions {
    public static List<InfoItem> fetchYouTubeSearchResults(final String query, final int maxPages) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Log.i("Youtube", "FAAAAAJH");

        Future<List<InfoItem>> future = executor.submit(() -> {
            List<InfoItem> results = new ArrayList<>();
            int fetchedPages = 1;

            try {
                NewPipe.init(DownloaderJurijImpl.getInstance());
                // 1 - YT, 14 - YTM
                YoutubeService youtubeService = new YoutubeService(14);
                YoutubeSearchQueryHandlerFactory factory = YoutubeSearchQueryHandlerFactory.getInstance();
                SearchQueryHandler handler = factory.fromQuery(query);

                SearchExtractor searchExtractor = youtubeService.getSearchExtractor(handler);
                searchExtractor.fetchPage();
                ListExtractor.InfoItemsPage<?> page = searchExtractor.getInitialPage();

                do {
                    results.addAll(page.getItems());
                    if (fetchedPages >= maxPages) {
                        break;
                    }

                    searchExtractor.fetchPage();
                    page = searchExtractor.getPage(page.getNextPage());
                    fetchedPages++;
                } while (page.hasNextPage());
            } catch (Exception e) {
                Log.e("YouTubeSearch", "Error fetching search results", e);
            }

            return results;
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("YouTubeSearch", "Error executing search", e);
            return new ArrayList<>();
        } finally {
            executor.shutdown();
        }
    }

    public static StreamInfo fetchYouTubeVideoMetadata(final String videoUrl) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<StreamInfo> future = executor.submit(() -> {
            try {
                NewPipe.init(DownloaderJurijImpl.getInstance());
                YoutubeService youtubeService = new YoutubeService(14);

                StreamInfo streamInfo = StreamInfo.getInfo(youtubeService, videoUrl);

                Log.i("YouTubeMetadata", "Title: " + streamInfo.getName());
                Log.i("YouTubeMetadata", "Uploader: " + streamInfo.getUploaderName());
                Log.i("YouTubeMetadata", "Upload date: " + streamInfo.getUploadDate());
                Log.i("YouTubeMetadata", "Duration (s): " + streamInfo.getDuration());
                Log.i("YouTubeMetadata", "Description: " + streamInfo.getDescription());
                Log.i("YouTubeMetadata", "Views: " + streamInfo.getViewCount());

                // List video streams
                List<VideoStream> videoStreams = streamInfo.getVideoStreams();
                for (VideoStream vs : videoStreams) {
                    Log.i("YouTubeMetadata", "Video Stream: " + vs.getResolution() + " - " + vs.getUrl());
                }

                // List audio streams
                List<AudioStream> audioStreams = streamInfo.getAudioStreams();
                for (AudioStream as : audioStreams) {
                    Log.i("YouTubeMetadata", "Audio Stream: " + as.getFormat() + " - " + as.getUrl());
                }

                return streamInfo;
            } catch (Exception e) {
                Log.e("YouTubeMetadata", "Error fetching video metadata", e);
                return null;
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("YouTubeMetadata", "Error executing metadata fetch", e);
            return null;
        } finally {
            executor.shutdown();
        }
    }

    public static List<StreamInfoItem> fetchPlaylistSongs(String playlistUrl, int maxPages) {
        //Log.i("YouTubeTest", "fetchPlaylistSongs");

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<StreamInfoItem>> future = executor.submit(() -> {
            List<StreamInfoItem> songs = new ArrayList<>();
            int fetchedPages = 1;

            try {
                NewPipe.init(DownloaderJurijImpl.getInstance());
                YoutubeService youtubeService = new YoutubeService(14); // YT Music works too

                PlaylistExtractor playlistExtractor =
                        youtubeService.getPlaylistExtractor(playlistUrl);

                playlistExtractor.fetchPage();

                ListExtractor.InfoItemsPage<StreamInfoItem> page =
                        playlistExtractor.getInitialPage();

                while (true) {
                    songs.addAll(page.getItems());

                    if (!page.hasNextPage() || fetchedPages >= maxPages) {
                        break;
                    }

                    page = playlistExtractor.getPage(page.getNextPage());
                    fetchedPages++;
                }

            } catch (Exception e) {
                Log.e("YouTubePlaylist", "Failed to fetch playlist", e);
            }

            return songs;
        });

        try {
            return future.get();
        } catch (Exception e) {
            Log.e("YouTubePlaylist", "Execution error", e);
            return new ArrayList<>();
        } finally {
            executor.shutdown();
        }
    }

}

/*
SEARCH:

lifecycleScope.launch {
    val results = JavaFunctions.fetchYouTubeSearchResults("Imagine Dragons Believer", 3)

    results.forEach { item ->
        Log.i("YouTubeSearch", "${item.name} - ${item.url}")
    }
}

URL METADATA:

lifecycleScope.launch {
    val info = JavaFunctions.fetchYouTubeVideoMetadata("https://www.youtube.com/watch?v=VIDEO_ID")
    info?.let {
        Log.i("YouTubeMetadata", "Title: ${it.name}")
        Log.i("YouTubeMetadata", "Uploader: ${it.uploaderName}")
    }
}

PLAYLIST SONGS:

lifecycleScope.launch {
    val songs = JavaFunctions.fetchPlaylistSongs(
        "https://www.youtube.com/playlist?list=PLOFzwFjTt5Qnzx4Zpib16igoWCaUiKHIT",
        5
    )

    songs.forEach {
        Log.i("YoutubePlaylistSong", "${it.name} - ${it.uploaderName}")
    }
}
*/
