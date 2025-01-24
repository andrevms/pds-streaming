import { useEffect, useState } from "react";
import ContentCell from "./ContentCell";
import { loadMusics, loadPodcasts } from "../util/Data";
import "./ContentLine.css";

export default function ContentLine(props) {
    const [contents, setContents] = useState([]);
    
    useEffect(() => {
        if (props.mediaType == `music`) {
            loadMusics().then((music) => {
                const musicList = [];
                for (let key in music) {
                    musicList.push(music[key]);
                }
                setContents(musicList);
            });
        }

        if (props.mediaType == `podcast`) {
            loadPodcasts().then((podcasts) => {
                const podcastsList = [];
                for (let key in podcasts) {
                    podcastsList.push(podcasts[key]);
                }
                setContents(podcastsList);
            })
        }
    });

    return (
        <div className="content-line">
            {contents.map((content) => (
                <ContentCell id={content.id} title={content.title} thumbnail={content.thumbnailUrl} animation={content.animationUrl} mediaType={props.mediaType} />
            ))}
        </div>
    );
}