import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router-dom";

import Member from "./Member";

function BoardEditor({ user }) {
	const [title, setTitle] = useState("");
	const [members, setMembers] = useState([]);
	const [memberToAdd, setMemberToAdd] = useState("");
	const [dummy, setDummy] = useState(0);
	const { name } = useParams();
	let history = useHistory();

	let count = 1;

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
				setTitle(resp.payload.name);
			})
			.catch(console.log);

		fetch(`/api/placardboards/${name}/members`, {
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

				throw new Error(
					"Could not load placard board: unexpected behaviour from the server."
				);
			})
			.then((resp) => {
				setMembers(resp.payload);
			})
			.catch(console.log);
	}, [dummy]);

	function deleteBoard() {
		return fetch(`/api/placardboards/${title}`, {
			method: "DELETE",
			headers: {
				"Content-Type": "application/json",
				Authorization: `Bearer ${user.token}`,
			},
		}).then((resp) => {
			if (resp.status === 200) return resp.json();
			if (resp.status === 404)
				throw new Error("The requested board could not be found");
			if (resp.status === 403)
				throw new Error(
					"The signed in user is not allowed to see or modify this board"
				);

			throw new Error(
				"Unexpected behaviour from the server. The board may or may not be deleted."
			);
		});
	}

	function handleDeleteRequest(e) {
		e.preventDefault();
		deleteBoard()
			.then((response) => {
				history.push("/board");
			})
			.catch(console.log);
	}

	function addMember() {
		return fetch(`/api/placardboards/${title}/members`, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				Authorization: `Bearer ${user.token}`,
			},
			body: JSON.stringify({ username: memberToAdd }),
		}).then((resp) => {
			if (resp.status === 200) return resp.json();
			if (resp.status === 404)
				throw new Error(
					"The board to which to add the user could not be found."
				);

			throw new Error(
				"Unexpected behaviour from the server. The user may or may not have been added as a member."
			);
		});
	}

	function handleAddMemberRequest(e) {
		e.preventDefault();
		addMember()
			.then((resp) => {
				setMemberToAdd("");
				setDummy(dummy + 1);
			})
			.catch(console.log);
	}

	function removeMember(member) {
		return fetch(`/api/placardboards/${title}/members/${member}`, {
			method: "DELETE",
			headers: {
				"Content-Type": "application/json",
				Authorization: `Bearer ${user.token}`,
			},
		}).then((resp) => {
			if (resp.status === 200) return resp.json();
			if (resp.status === 404) throw new Error("404");
			if (resp.status === 403) throw new Error("403");

			throw new Error(
				"Unexpected behaviour from the server. The member may or may not be deleted."
			);
		});
	}

	function handleRemoveMember(member) {
		removeMember(member)
			.then((resp) => {
				setDummy(dummy + 1);
			})
			.catch(console.log);
	}

	function handleMemberInput(e) {
		setMemberToAdd(e.target.value);
	}

	return (
		<section>
			<h4>Board editor for {title}</h4>

			<h5>Settings</h5>
			<button onClick={handleDeleteRequest}>Delete this board</button>

			<h5>Members</h5>
			<form onSubmit={handleAddMemberRequest}>
				<label htmlFor="board-member-field">Member to add</label>
				<input
					value={memberToAdd}
					onChange={handleMemberInput}
					className="board-input-member"
					type="text"
					id="board-member-field"
					name="board-member-field"
					placeholder="Member name..."
				/>
				<button type="submit">Add member</button>
			</form>
			<h6>Current members</h6>
			<ul className="members-list">
				{members.map((member) => (
					<li key={`${member}${count++}`}>
						<Member member={member} onRemove={handleRemoveMember} />
					</li>
				))}
			</ul>
		</section>
	);
}

export default BoardEditor;
