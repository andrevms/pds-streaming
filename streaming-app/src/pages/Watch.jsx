import "./Watch.css";
import React, { useState, useEffect } from "react";
import { loadEpisode, loadMovie, loadEpisodes, loadMovies } from "../util/Data";
import { useOutletContext, useParams } from "react-router-dom";

export default function Watch() {
    const [width, setWidth] = useState(window.innerWidth * 0.75);
    const [subject, setSubject] = useState();
    const [video, setVideo] = useState(null);
    const [quiz, setQuiz] = useState();

    const { mediaType, mediaId } = useParams();

    const { updateTitle } = useOutletContext();

    useEffect(() => {
        if (mediaType == `tvshow` || mediaType == `tvShow`) {
            loadEpisodes().then((episodes) => {
                for (let key in episodes) {
                    if (episodes[key].id == mediaId) {
                        updateTitle(episodes[key].title);
                        setSubject(episodes[key].description);
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
                        setSubject(movies[key].description);
                        setVideo(movies[key].videoUrl);
                    }
                }
            });
        }

        /*fetch(`http://localhost:8080/api/ask-llm-quiz`, {
            method: "POST",
            body: {
                subject: subject,
                description: video
            }
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network answer was not ok.");
            }
            return response.json();
        })
        .then((response) => {
            console.log(response);
        });*/

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