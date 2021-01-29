import { useState, useEffect } from "react";
import { useParams, useHistory } from "react-router-dom";

function PlacardEditor({ user }) {
	let { name } = useParams();
	const [title, setTitle] = useState("");
	const [text, setText] = useState("");

	let history = useHistory();

	useEffect(() => {
		fetch(`/api/placards/${name}`, {
			method: "GET",
			headers: {
				"Content-Type": "application/json",
				Accept: "application/json",
				Authorization: `Bearer ${user.token}`,
			},
		})
			.then((response) => {
				if (!(response.status === 200)) {
					throw new Error("Failed to fetch resource");
				}

				return response.json();
			})
			.then((response) => {
				setTitle(response.payload.header);
				setText(response.payload.text);
			})
			.catch(console.log);
	}, []);

	function handleTitleInput(e) {
		setTitle(e.target.value);
	}

	function handleTextInput(e) {
		setText(e.target.value);
	}

	function putPlacard() {
		return fetch(`/api/placards/${name}`, {
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
				Accept: "application/json",
				Authorization: `Bearer ${user.token}`,
			},
			body: JSON.stringify({ header: title, text }),
		}).then((response) => {
			if (!(response.status === 200)) {
				throw new Error("Failed to update placard");
			}
			return response.json();
		});
	}

	function handleSubmit(e) {
		e.preventDefault();
		putPlacard()
			.then((resp) => {
				history.push(`/board`);
			})
			.catch((e) => console.log(e));
	}

	return (
		<section>
			<h4>Placard Editor for {title}</h4>
			<h6>{title.length > 0 ? title : "Placard title"}</h6>
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

export default PlacardEditor;
