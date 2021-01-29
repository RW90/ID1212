const ALERT_COLORS = {
	SUCCESS: "green",
};

function Alert({ message }) {
	return (
		<div
			className="container"
			style={{
				position: "fixed",
				top: "3rem",
				backgroundColor: ALERT_COLORS.SUCCESS,
				padding: "1rem",
			}}
		>
			could not load resource
		</div>
	);
}

export default Alert;
