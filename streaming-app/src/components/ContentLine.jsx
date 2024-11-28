import { useEffect, useState } from "react";
import ContentCell from "./ContentCell";
import { loadMovies, loadTvShows } from "../util/Data";
import "./ContentLine.css";

export default function ContentLine(props) {
    const [contents, setContents] = useState([]);
    const [isHovered, setIsHovered] = useState(false);

    const handleMouseEnter = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    const loadData = () => {

    };

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
                <ContentCell onMouseOver={handleMouseEnter} onMouseLeave={handleMouseLeave} image={(isHovered && content.animationUrl) ? content.animationUrl : (content.thumbnailUrl || "https://placehold.jp/20/bb1111/ffffff/120x200.png?text=no+image")} />
            ))}
        </div>
    );
}