import "./Listen.css";
import React, { useState, useEffect } from "react";
import { loadEpisodes, loadMovies } from "../util/Data";
import { useOutletContext, useParams } from "react-router-dom";

export default function Watch() {
    const [width, setWidth] = useState(window.innerWidth * 0.75);
    const [subject, setSubject] = useState();
    const [video, setVideo] = useState(null);
    const [quiz, setQuiz] = useState();

    const { mediaType, mediaId } = useParams();

    const { updateTitle } = useOutletContext();

    useEffect(() => {
        if (mediaType == `music`) {
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

        if (mediaType == `podcast`) {
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
            {localStorage.getItem("username") ? (video ? <video className="player-video" style={{ width: width, height: width / 1.7 }} src={video} controls autoPlay></video> : <p className="video-warning-text">Vídeo não encontrado.</p>) : <p className="video-warning-text">Você precisa estar logado para ter acesso ao conteúdo.</p>}
        </div>
    );
}