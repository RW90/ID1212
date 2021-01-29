import { useState } from "react";
import { Link } from "react-router-dom";

function Register({ onRegistration }) {
	const [state, setState] = useState({ username: "", password: "" });

	function handleInput(e) {
		setState({ ...state, [e.target.name]: e.target.value });
	}

	function register() {
		return fetch("/api/users", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				username: state.username,
				password: state.password,
			}),
		}).then((response) => {
			if (response.status === 201) return response.json();
			if (response.status === 200)
				throw new Error("Username is not available");
			if (response.status === 400)
				throw new Error(
					"Password or username not between 5-20 characters"
				);

			throw new Error("Unexpected response from server");
		});
	}

	function handleSubmit(e) {
		e.preventDefault();
		register()
			.then(({ token, ...rest }) => {
				onRegistration({ ...state, token });
			})
			.catch((e) => {
				console.log(e);
			});
	}

	return (
		<section>
			<h5>Register</h5>
			<form onSubmit={handleSubmit}>
				<div>
					<label htmlFor="register-username-field">Username</label>
					<input
						value={state.username}
						onChange={handleInput}
						className="access-input"
						type="text"
						id="register-username-field"
						name="username"
						placeholder="Enter username..."
					/>
				</div>
				<div>
					<label htmlFor="register-password-field">Password</label>
					<input
						value={state.password}
						onChange={handleInput}
						className="access-input"
						type="password"
						id="register-password-field"
						name="password"
						placeholder="Enter password..."
					/>
				</div>
				<button type="submit" className="button-primary">
					Register account
				</button>
			</form>
			<p>
				Already have an account? <Link to="/login">Sign in here</Link>.
			</p>
		</section>
	);
}

export default Register;
