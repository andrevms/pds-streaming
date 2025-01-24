import "./ContentCell.css";
import { Link } from "react-router-dom";
import { useState } from "react";

export default function ContentCell(props) {
    const noImage = "https://placehold.jp/20/2f0/ffffff/120x200.png?text=no+image";
    const [isHovered, setIsHovered] = useState(false);

    const handleMouseEnter = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    return (
        <div>
            {props.mediaType == `music` && <Link to={`/listen/music/${props.id}`}>
                <img className="content-cell-image" onMouseOver={handleMouseEnter} onMouseLeave={handleMouseLeave} src={(isHovered && props.animation) ? props.animation : (props.thumbnail || noImage)}></img>
            </Link>}
            {props.mediaType == `podcast` && <Link to={`/listen/podcast/${props.id}`}>
                <img className="content-cell-image" onMouseOver={handleMouseEnter} onMouseLeave={handleMouseLeave} src={(isHovered && props.animation) ? props.animation : (props.thumbnail || noImage)}></img>
            </Link>}
        </div>
    );
}