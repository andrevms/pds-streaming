import "./ContentCell.css";
import { useState } from "react";

export default function ContentCell(props) {
    const [isHovered, setIsHovered] = useState(false);

    const handleMouseEnter = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    return (
        <div>
            <img className="content-cell-image" onMouseOver={handleMouseEnter} onMouseLeave={handleMouseLeave} src={(isHovered && props.animation) ? props.animation : (props.thumbnail || "https://placehold.jp/20/bb1111/ffffff/120x200.png?text=no+image")}></img>
        </div>
    );
}