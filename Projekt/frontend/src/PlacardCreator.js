import { useState } from "react";
import { useParams, useHistory } from "react-router-dom";

function PlacardCreator({ user }) {
	let { board } = useParams();
	const [title, setTitle] = useState("");
	const [text, setText] = useState("");

	let history = useHistory();

	function handleTitleInput(e) {
		setTitle(e.target.value);
	}

	function handleTextInput(e) {
		setText(e.target.value);
	}

	function postPlacard() {
		return fetch(`/api/placards/placardboard/${board}`, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				Accept: "application/json",
				Authorization: `Bearer ${user.token}`,
			},
			body: JSON.stringify({ header: title, text }),
		}).then((response) => {
			if (!(response.status === 201)) {
				throw new Error("Failed to set title");
			}
			return response.json();
		});
	}

	function handleSubmit(e) {
		e.preventDefault();
		postPlacard()
			.then((resp) => {
				history.push(`/board/${board}`);
			})
			.catch((e) => console.log(e));
	}

	return (
		<section>
			<h4>Placard Creator</h4>
			<h6>
				{title.length > 0 ? title : "Placard title"} at the board{" "}
				{board}
			</h6>
			<form onSubmit={handleSubmit}>
				<div>
					<label htmlFor="placard-header-field">Placard title</label>
					<input
						value={title}
						onChange={handleTitleInput}
						className="placard-input-title"
						type="text"
						id="placard-header-field"
						name="placard-header-field"
						placeholder="Title of placard..."
					/>
					<label htmlFor="placard-text-field">Text</label>
					<textarea
						value={text}
						onChange={handleTextInput}
						className="placard-input-text"
						id="placard-text-field"
						name="placard-text-field"
						placeholder="Text on the placard..."
					></textarea>
				</div>
				<button type="submit" className="button-primary">
					Submit
				</button>
			</form>
		</section>
	);
}

export default PlacardCreator;
