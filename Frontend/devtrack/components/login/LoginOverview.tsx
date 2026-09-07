export default function LoginOverview() {
    return (
        <>
            <h1 className="page-header">Login</h1>
            <form>
                <div>
                    <label htmlFor="usernameInput">Username:</label>
                    <input type="text" id="usernameInput" name="username" placeholder="Enter username" required/>
                </div>
                <div>
                    <label htmlFor="passwordInput">Password:</label>
                    <input type="password" id="passwordInput" name="password" placeholder="Enter password" required/>
                </div>
                <button type="submit">Login</button>
            </form>
        </>
    )
}