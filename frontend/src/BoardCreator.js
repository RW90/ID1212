import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router-dom";

function BoardCreator({ user }) {
	const [title, setTitle] = useState("");
	const { name } = useParams();
	let history = useHistory();

	function handleTitleInput(e) {
		setTitle(e.target.value);
	}

	function submitBoardTitle() {
		return fetch("/api/placardboards/", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				Accept: "application/json",
				Authorization: `Bearer ${user.token}`,
			},
			body: JSON.stringify({ name: title, isPublic: true }),
		}).then((response) => {
			if (response.status === 201) return response.json();
			if (response.status === 409)
				throw new Error(
					"A board with that name already exists (board names must be unique)"
				);
			if (response.status === 400)
				throw new Error(
					"The server interpreted the request as not valid"
				);

			throw Error(
				"Could not create board: unexpected behaviour from the server"
			);
		});
	}

	function handleTitleSubmit(e) {
		e.preventDefault();
		submitBoardTitle()
			.then((resp) => {
				history.push(`/boardeditor/${title}`);
			})
			.catch((e) => console.log(e));
	}

	return (
		<section>
			<h4>Board creator</h4>
			<h6>{title.length > 0 ? title : "Title of the board"}</h6>
			<form onSubmit={handleTitleSubmit}>
				<label htmlFor="board-title-field">Board title</label>
				<input
					value={title}
					onChange={handleTitleInput}
					className="board-input-title"
					type="text"
					id="board-title-field"
					name="board-title-field"
					placeholder="Title of board..."
				/>
				<button type="submit" className="button-primary">
					Create
				</button>
			</form>
		</section>
	);
}

export default BoardCreator;
