import { useState, useEffect } from "react";
import { Switch, Route, Redirect, useHistory } from "react-router-dom";

import Navigation from "./Navigation";
import BoardsOverview from "./BoardsOverview";
import Start from "./Start";
import PlacardCreator from "./PlacardCreator";
import BoardCreator from "./BoardCreator";
import BoardEditor from "./BoardEditor";
import Login from "./Login";
import Register from "./Register";
import Account from "./Account";
import Alert from "./Alert";
import Board from "./Board";
import PlacardEditor from "./PlacardEditor";

import "./App.css";

function App() {
	const showAlert = false;
	const [state, setState] = useState({
		user: { username: "", token: "" },
		userIsSignedIn: false,
	});

	let history = useHistory();

	useEffect(() => {
		if (state.userIsSignedIn) {
			history.push("/start");
		}
	});

	function signInUser(user) {
		setState({ ...state, user, userIsSignedIn: true });
	}

	function signOutUser() {
		setState({
			...state,
			user: { username: "", token: "" },
			userIsSignedIn: false,
		});
	}

	function requestDelete() {
		return fetch(`/api/users/${state.user.username}`, {
			method: "DELETE",
			headers: {
				"Content-Type": "application/json",
				Authorization: `Bearer ${state.user.token}`,
			},
		}).then((response) => {
			if (!(response.status === 200)) {
				throw new Error(`Could not delete the signed in account`);
			}
			return response.json();
		});
	}

	function deleteUser() {
		requestDelete()
			.then(() => signOutUser())
			.catch(console.log);
	}

	return (
		<div className="App container">
			{showAlert && <Alert />}
			<header className="header-main">
				<h4>Placard Board</h4>
				<Navigation />
			</header>
			<main>
				<Switch>
					<Route exact path="/">
						{state.userIsSignedIn ? (
							<Start user={state.user} />
						) : (
							<Redirect to="/login" />
						)}
					</Route>
					<Route path="/login">
						<Login onSignIn={signInUser} />
					</Route>
					<Route path="/register">
						<Register onRegistration={signInUser} />
					</Route>
					{state.userIsSignedIn && (
						<>
							<Route path="/start">
								<Start user={state.user} />
							</Route>
							<Route path="/board/:name">
								<Board user={state.user} />
							</Route>
							<Route exact path="/board">
								<BoardsOverview user={state.user} />
							</Route>
							<Route path="/placardcreator/:board">
								<PlacardCreator user={state.user} />
							</Route>
							<Route path="/boardeditor/:name">
								<BoardEditor user={state.user} />
							</Route>
							<Route exact path="/boardcreator">
								<BoardCreator user={state.user} />
							</Route>
							<Route path="/placardeditor/:name">
								<PlacardEditor user={state.user} />
							</Route>
							<Route path="/account">
								<Account
									onDelete={deleteUser}
									onSignOut={signOutUser}
									user={state.user}
								/>
							</Route>
						</>
					)}
					<Route path="/*">
						<Redirect to="/login" />
					</Route>
				</Switch>
			</main>
		</div>
	);
}

export default App;
