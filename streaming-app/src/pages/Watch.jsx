import "./Watch.css";
import React, { useState, useEffect } from "react";
import { loadEpisode, loadMovie, loadEpisodes, loadMovies } from "../util/Data";
import { useOutletContext, useParams } from "react-router-dom";

export default function Watch(props) {
    const [width, setWidth] = useState(window.innerWidth * 0.75);
    const [video, setVideo] = useState(null);

    const { mediaType, mediaId } = useParams();

    const { updateTitle } = useOutletContext();

    useEffect(() => {
        /*if (props.mediaType == `episode`) {
            loadEpisode(props.id).then((episode) => {
                setVideo(episode.videoUrl);
                document.title = `${episode.title} | Blockburst`;
                updateTitle(`${episode.title}`);
            });
        }

        if (props.mediaType == `movie`) {
            loadMovie(props.id).then((movie) => {
                setVideo(movie.videoUrl);
                document.title = `${movie.title} | Blockburst`;
                updateTitle(`${episode.title}`);
            });
        }*/
    
        if (mediaType == `tvshow` || mediaType == `tvShow`) {
            loadEpisodes().then((episodes) => {
                for (let key in episodes) {
                    if (episodes[key].id == mediaId) {
                        updateTitle(episodes[key].title);
                        setVideo(episodes[key].videoUrl);
                    }
                }
            });
        }

        if (mediaType == `movie`) {
            loadMovies().then((movies) => {
                for (let key in movies) {
                    if (movies[key].id == mediaId) {
                        updateTitle(movies[key].title);
                        setVideo(movies[key].videoUrl);
                    }
                }
            });
        }

        const handleResize = () => {
            setWidth(window.innerWidth * 0.75);
        };

        window.addEventListener('resize', handleResize);

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    return (
        <div className="watch-page">
            {video ? <video className="player-video" style={{ width: width, height: width/1.7 }} src={video} controls autoPlay></video> : <p className="video-not-found-text">Vídeo não encontrado.</p>}
        </div>
    );
}