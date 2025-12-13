export const dynamic = 'force-static';

export default function Calender() {
    return (
        <>
            <h1>Edit</h1>
            <h2>Calendar</h2>
            <form>
                <label htmlFor="week">週</label>
                <input type="week" id="week" name="week" />
                <button type="button">前へ</button>
                <button type="button">今週</button>
                <button type="button">次へ</button>
            </form>
            <table>
                <thead>
                    <tr>
                        <th>月</th>
                        <th>火</th>
                        <th>水</th>
                        <th>木</th>
                        <th>金</th>
                        <th>土</th>
                        <th>日</th>
                    </tr>
                </thead>
            </table>
        </>
    );
}