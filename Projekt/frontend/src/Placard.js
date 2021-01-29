import { Link } from "react-router-dom";

function Placard({ placard, user, boardowner, onDelete }) {
	return (
		<article className="placard">
			<h5 className="placard-header">{placard.header}</h5>
			<span className="placard-author">
				<strong>Author:</strong>{" "}
				<span className="italics">{placard.user}</span>
			</span>
			<p>{placard.text}</p>
			<ul className="placard-actions-list">
				{(placard.user === user.username ||
					boardowner === user.username) && (
					<li className="placard-action-list-item">
						<button onClick={() => onDelete(placard.id)}>
							Delete
						</button>
					</li>
				)}
				{placard.user === user.username && (
					<li className="placard-action-list-item">
						<Link to={`/placardeditor/${placard.id}`}>
							<button className="button-primary">Edit</button>
						</Link>
					</li>
				)}
			</ul>
		</article>
	);
}

export default Placard;
