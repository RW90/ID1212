import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";

import Placard from "./Placard";

function Board({ user }) {
	const { name } = useParams();
	const [owner, setOwner] = useState("");
	const [dummy, setDummy] = useState(1);
	const [placards, setPlacards] = useState([]);

	useEffect(() => {
		fetch(`/api/placardboards/${name}`, {
			method: "GET",
			headers: {
				"Content-Type": "application/json",
				Authorization: `Bearer ${user.token}`,
			},
		})
			.then((resp) => {
				if (resp.status === 200) return resp.json();
				if (resp.status === 404)
					throw new Error("The requested board does not exist");
				if (resp.status === 403)
					throw new Error(
						"The signed in user is not allowed to see this board"
					);

				throw new Error(
					"Could not load placard board: unexpected behaviour from the server."
				);
			})
			.then((resp) => {
				setPlacards(resp.payload.placards);
				setOwner(resp.payload.owner);
			})
			.catch(console.log);
	}, [user, name, dummy]);

	function deletePlacard(id) {
		return fetch(`/api/placards/${id}`, {
			method: "DELETE",
			headers: {
				"Content-Type": "application/json",
				Authorization: `Bearer ${user.token}`,
			},
		}).then((resp) => {
			if (resp.status === 200) return resp.json();
			throw new Error("Could not perform request");
		});
	}

	function handleDelete(id) {
		deletePlacard(id)
			.then(() => setDummy(dummy + 1))
			.catch(console.log);
	}

	return (
		<section>
			<h4>{name}</h4>
			<div className="button-row">
				{user.username === owner && (
					<Link to={`/boardeditor/${name}`}>
						<button>Edit board</button>
					</Link>
				)}
				<Link to={`/placardcreator/${name}`}>
					<button className="button-primary">Make new post</button>
				</Link>
			</div>
			<ul className="board-preview-list">
				{placards.map((placard) => (
					<li key={placard.id}>
						<Placard
							placard={placard}
							user={user}
							boardowner={owner}
							onDelete={handleDelete}
						/>
					</li>
				))}
			</ul>
		</section>
	);
}

export default Board;
