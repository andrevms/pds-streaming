import "./Watch.css";
import React, { useState, useEffect } from "react";

export default function Watch(props) {
    const [width, setWidth] = useState(window.innerWidth * 0.75);

    useEffect(() => {
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
            {props.video ? <video className="player-video" style={{ width: width, height: width/1.7 }} src={props.video} controls autoPlay></video> : <p className="video-not-found-text">Vídeo não encontrado.</p>}
        </div>
    );
}