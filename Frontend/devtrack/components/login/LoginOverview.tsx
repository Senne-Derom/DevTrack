export default function LoginOverview() {
    return (
        <div className="form-card panel">
            <div className="page-header">
                <h1>Login</h1>
            </div>
            <form className="form">
                <div>
                    <label htmlFor="usernameInput" className="form-label">Username:</label>
                    <input type="text" id="usernameInput" name="username" placeholder="Enter username" required/>
                </div>
                <div>
                    <label htmlFor="passwordInput" className="form-label">Password:</label>
                    <input type="password" id="passwordInput" name="password" placeholder="Enter password" required/>
                </div>
                <button type="submit" className="form-submit">Login</button>
            </form>
        </div>
    )
}