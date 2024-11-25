import { useState } from "react";
import ContentCell from "./ContentCell";
import "ContentLine.css";

export default function ContentLine() {
    const [contents, setContents] = useState([]);

    const loadData = () => {

    };

    return (
        <div className="content-line">
            {contents.map((content) => (
                <ContentCell image={content.thumbnailUrl || "https://placehold.jp/20/bb1111/ffffff/120x200.png?text=no+image"} />
            ))}
        </div>
    );
}