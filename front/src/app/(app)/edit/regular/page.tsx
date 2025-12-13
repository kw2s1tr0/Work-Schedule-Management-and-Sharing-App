export const dynamic = 'force-static';

export default function Regular() {
    return (
        <>
            <h1>Edit</h1>
            <h2>Regular</h2>
            <form>
                <label htmlFor="from">開始日</label>
                <input type="date" id="from" name="from" />
                <label htmlFor="to">終了日</label>
                <input type="date" id="to" name="to" />
                <button type="button">検索</button>
            </form>
        </>
    );
}