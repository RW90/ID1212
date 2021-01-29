import { useState } from "react";
import { Link } from "react-router-dom";
import jwt_decode from "jwt-decode";

function Login({ onSignIn }) {
	const [state, setState] = useState({ username: "", password: "" });

	function handleInput(e) {
		setState({ ...state, [e.target.name]: e.target.value });
	}

	function authenticate() {
		return fetch("/api/authenticate", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				Accept: "application/json",
			},
			body: JSON.stringify(state),
		}).then((response) => {
			if (!(response.status === 200)) throw new Error("Failed to login");
			return response.json();
		});
	}

	function handleSubmit(e) {
		e.preventDefault();

		authenticate()
			.then(({ jwtToken }) => {
				onSignIn({
					username: jwt_decode(jwtToken).sub,
					token: jwtToken,
				});
			})
			.catch(console.log);
	}

	return (
		<section>
			<h5>Sign in</h5>
			<form onSubmit={handleSubmit}>
				<div>
					<label htmlFor="login-username-field">Username</label>
					<input
						value={state.username}
						onChange={handleInput}
						className="access-input"
						type="text"
						id="login-username-field"
						name="username"
						placeholder="Enter username..."
					/>
				</div>
				<div>
					<label htmlFor="login-password-field">Password</label>
					<input
						value={state.password}
						onChange={handleInput}
						className="access-input"
						type="password"
						id="login-password-field"
						name="password"
						placeholder="Enter password..."
					/>
				</div>
				<button type="submit" className="button-primary">
					Sign in
				</button>
			</form>
			<p>
				Don't have an account yet?{" "}
				<Link to="/register">Register here</Link>.
			</p>
		</section>
	);
}

export default Login;
