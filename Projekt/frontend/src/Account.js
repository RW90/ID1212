function Account({ onDelete, onSignOut, user }) {
	return (
		<section>
			<h4>My account</h4>
			<p>Signed in as {user.username}</p>
			<button onClick={onSignOut} className="button-primary">
				Sign out
			</button>
			<h6>Delete account</h6>
			<button onClick={onDelete}>Delete my account</button>
		</section>
	);
}

export default Account;
