function Member({ member, onRemove }) {
	return (
		<>
			<span>{member}</span>
			<button onClick={() => onRemove(member)}>Remove</button>
		</>
	);
}

export default Member;
