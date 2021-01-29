import { Link } from "react-router-dom";

function Navigation() {
	return (
		<nav>
			<ul className="nav">
				<li className="nav-link">
					<Link to="/start">
						<button className="nav-button">Start</button>
					</Link>
				</li>
				<li className="nav-link">
					<Link to="/board">
						<button className="nav-button">Boards</button>
					</Link>
				</li>
				<li className="nav-link">
					<Link to="/account">
						<button className="nav-button">Account</button>
					</Link>
				</li>
			</ul>
		</nav>
	);
}

export default Navigation;
