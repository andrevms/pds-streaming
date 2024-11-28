import React, { useState } from "react";
import "./Logotype.css";

export default function Logotype(props) {
    const [isHovered, setIsHovered] = useState(false);
    const color = isHovered ? "#a10505" : "#bb1111";

    const handleMouseEnter = () => {
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setIsHovered(false);
    };

    return (
        <svg viewBox="0 0 206 48" xmlns="http://www.w3.org/2000/svg">
            <g transform="matrix(1, 0, 0, 1, -104.663849, -9.314347)">
                <rect className="logotype-pointable" onMouseOver={handleMouseEnter} onMouseLeave={handleMouseLeave} x="108.9" y="12.9" width="18.7" height="18.7" style={{ stroke: "rgb(0, 0, 0)", strokeWidth: "0px", fill: color }} />
                <rect className="logotype-pointable" onMouseOver={handleMouseEnter} onMouseLeave={handleMouseLeave} x="159.45" y="30.9" width="21" height="21" style={{ stroke: "rgb(0, 0, 0)", strokeWidth: "0px", fill: color }} />
                <text className="logotype-pointable" onMouseOver={handleMouseEnter} onMouseLeave={handleMouseLeave} style={{ fill: color, fontFamily: "Outfit", fontSize: "28px", fontWeight: 600, whiteSpace: "pre" }}>
                    <tspan x="125.521" y="51.243">BL</tspan>
                    <tspan style={{ fill: props.letterocolor }}>O</tspan>
                    <tspan>CKBURST</tspan>
                </text>
            </g>
        </svg>
    );
}
