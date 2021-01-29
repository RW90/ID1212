import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function BoardsOverview({ user }) {
	const [ownBoards, setOwnBoards] = useState([]);
	const [memberships, setMemberships] = useState([]);

	useEffect(() => {
		fetch(`/api/users/${user.username}`, {
			method: "GET",
			headers: {
				"Content-Type": "application/json",
				Authorization: `Bearer ${user.token}`,
			},
		})
			.then((resp) => resp.json())
			.then((resp) => {
				setOwnBoards(resp.payload.boardsOwned);
				setMemberships(resp.payload.boardsWhereIsMember);
			});
	}, [user]);

	return (
		<section>
			<h4>Boards</h4>
			<div>
				<Link to="/boardcreator">
					<button className="button-primary">Create new board</button>
				</Link>
			</div>
			<h6>My boards</h6>
			<ul className="board-preview-list">
				{ownBoards.map((board) => (
					<li key={board.name}>
						<Link to={`/board/${board.name}`}>{board.name}</Link>
					</li>
				))}
			</ul>
			<h6>My board memberships</h6>
			<ul className="board-preview-list">
				{memberships.map((board) => (
					<li key={board.name}>
						<Link to={`/board/${board.name}`}>{board.name}</Link>
					</li>
				))}
			</ul>
		</section>
	);
}

export default BoardsOverview;
