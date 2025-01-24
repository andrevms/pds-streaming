import "./Listen.css";
import React, { useState, useEffect } from "react";
import { loadMusics, loadPodcasts } from "../util/Data";
import { useOutletContext, useParams } from "react-router-dom";

export default function Listen() {
    const [subject, setSubject] = useState();
    const [audio, setAudio] = useState(null);

    const { mediaType, mediaId } = useParams();

    const { updateTitle } = useOutletContext();

    useEffect(() => {
        if (mediaType == `music`) {
            loadMusics().then((music) => {
                for (let key in music) {
                    if (music[key].id == mediaId) {
                        updateTitle(`${music[key].artists} - ${music[key].title}`);
                        document.title = `${music[key].artists} - ${music[key].title}`;
                        setSubject(music[key].description);
                        setAudio(music[key].audioUrl);
                    }
                }
            });
        }

        if (mediaType == `podcast`) {
            loadPodcasts().then((podcasts) => {
                for (let key in podcasts) {
                    if (podcasts[key].id == mediaId) {
                        updateTitle(podcasts[key].title);
                        setSubject(podcasts[key].description);
                        setAudio(podcasts[key].audioUrl);
                    }
                }
            });
        }
    }, []);

    return (
        <div className="listen-page">
            {localStorage.getItem("username") ? (audio ? <audio controls><source src={audio}></source></audio> : <p className="audio-warning-text">Áudio não encontrado.</p>) : <p className="audio-warning-text">Você precisa estar logado para ter acesso ao conteúdo.</p>}
        </div>
    );
}