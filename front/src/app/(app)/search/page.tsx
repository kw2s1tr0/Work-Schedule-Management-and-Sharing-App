export const dynamic = 'force-dynamic';

export default async function Search() {
    return (
        <>
            <h1>Search</h1>
            <form>
                <label htmlFor="userId">ユーザーID</label>
                <input type="text" id="userId" name="userId"/>
                <label htmlFor="week">期間</label>
                <input type="text" id="week" name="week" />
                <label htmlFor="name">名前</label>
                <input type="text" id="name" name="name" />
                <label htmlFor="organizationCode">組織コード</label>
                <select name="organizationCode" id="organizationCode"></select>
                <button type="button">Search</button>
            </form>
        </>
    );
}