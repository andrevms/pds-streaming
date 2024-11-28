import { useState } from "react";
import ContentCell from "./ContentCell";
import "ContentLine.css";

export default function ContentLine() {
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

    return (
        <div className="content-line">
            {contents.map((content) => (
                <ContentCell image={(isHovered && content.animationUrl) ? content.animationUrl : (content.thumbnailUrl || "https://placehold.jp/20/bb1111/ffffff/120x200.png?text=no+image")} />
            ))}
        </div>
    );
}