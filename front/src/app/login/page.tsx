export const dynamic = 'force-static';

export default function Login() {
    return (
        <>
            <h1>WSMSA</h1>
            <h2>Login Page</h2>
            <form>
                <label htmlFor="username">Username</label>
                <input type="text" id="username" name="username"/>
                <label htmlFor="password">Password</label>
                <input type="password" id="password" name="password"/>
                <button type="button">Login</button>
            </form>
        </>
    );
}