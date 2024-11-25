import "ContentCell.css";

export default function ContentCell(props) {
    return (
        <div>
            <img className="content-cell-image" src={props.image}></img>
        </div>
    );
}