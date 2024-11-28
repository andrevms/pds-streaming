import { useEffect, useState } from "react";
import ContentCell from "./ContentCell";
import { loadMovies, loadTvShows } from "../util/Data";
import "./ContentLine.css";

export default function ContentLine(props) {
    const [contents, setContents] = useState([]);
    
    useEffect(() => {
        if (props.mediaType == `tvshow`) {
            loadTvShows().then((tvShows) => {
                const tvShowsList = [];
                for (let key in tvShows) {
                    tvShowsList.push(tvShows[key]);
                }
                setContents(tvShowsList);
            });
        }

        if (props.mediaType == `movie`) {
            loadMovies().then((movies) => {
                const moviesList = [];
                for (let key in movies) {
                    moviesList.push(movies[key]);
                }
                setContents(moviesList);
            })
        }
    });

    return (
        <div className="content-line">
            {contents.map((content) => (
                <ContentCell thumbnail={content.thumbnailUrl} animation={content.animationUrl} />
            ))}
        </div>
    );
}