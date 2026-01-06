# Work-Schedule-Management-and-Sharing-App

## 概要

本アプリケーションは、従業員の勤怠スケジュールを管理・共有するためのWebアプリケーションです。
ユーザーは自身のスケジュールを登録・更新・削除でき、組織内の他メンバーのスケジュールを検索・閲覧できます。

### 主な機能

- **認証機能**: セッションベースの認証（Spring Security + JSESSIONID）
- **スケジュール管理**:
  - **非定期スケジュール**: 特定の日付のスケジュール（有給休暇、外出など）
  - **定期スケジュール**: 曜日ベースの繰り返しスケジュール（毎週月曜在宅勤務など）
  - **デフォルトスケジュール**: 基本的な勤務スケジュール
  - **共通スケジュール**: 組織全体に適用されるスケジュール
- **スケジュール検索**: 週次・月次でのスケジュール一覧表示
- **フィルタリング**: ユーザーID、氏名、組織による絞り込み
- **マスタデータ管理**: 組織情報、勤怠種別の取得

## 技術スタック

### バックエンド

- **言語**: Java 21
- **フレームワーク**: Spring Boot 3.2.5
  - Spring Web (REST API)
  - Spring Security (認証・認可)
  - Spring JDBC
- **ORM**: MyBatis 3.0.3
- **データベース**: PostgreSQL
- **ビルドツール**: Gradle 9.1.0
- **コード整形**: Spotless (Google Java Format)
- **その他ライブラリ**: Lombok, Jakarta Validation

### フロントエンド

- **言語**: TypeScript 5
- **フレームワーク**: Next.js 16.0.6 (React 19)
- **日付操作**: date-fns 4.1.0
- **スタイリング**: CSS Modules
- **コード品質**: ESLint, Prettier

### インフラ

- **開発環境**: Docker Compose（想定）
- **データベース**: PostgreSQL (ポート5432)
- **APIサーバー**: Spring Boot (ポート8080)
- **フロントエンドサーバー**: Next.js Dev Server

## アーキテクチャ

### バックエンド構成

```
com.schedule.app/
├── controller/          # REST APIエンドポイント
├── applicationservice/  # アプリケーションサービス（ユースケース実装）
├── domainservice/       # ドメインサービス（ビジネスロジック）
├── repository/          # データアクセス層（MyBatis Mapper）
├── entity/              # ドメインエンティティ
├── dto/                 # データ転送オブジェクト
├── form/                # リクエストフォーム（バリデーション）
├── record/              # レコード型（input/output）
├── enums/               # 列挙型
├── exception/           # カスタム例外
├── handler/             # 例外ハンドラ
├── security/            # セキュリティ設定
└── config/              # アプリケーション設定
```

**レイヤー構成**:
- Controller → Application Service → Domain Service → Repository
- DTOを用いたレイヤー間のデータ受け渡し
- Domain層でのビジネスルール検証（DomainException）

### フロントエンド構成

```
front/src/
├── app/                # Next.js App Router
│   ├── (app)/         # 認証後の画面
│   │   ├── search/    # スケジュール検索画面
│   │   └── edit/      # スケジュール編集画面
│   ├── login/         # ログイン画面
│   └── api/           # API Routes（プロキシ）
├── usecase/           # ビジネスロジック（サーバー/クライアント両対応）
├── type/
│   ├── dto/           # DTOの型定義
│   ├── res/           # レスポンスの型定義
│   ├── req/           # リクエストの型定義
│   └── form/          # フォームの型定義
├── fetch/             # API通信（fetch wrapper）
├── enum/              # 列挙型
└── Error/             # カスタムエラー
```

**設計パターン**:
- Server Components中心のSSR設計
- Usecase層によるビジネスロジックの分離
- ServerOrClientEnumによるサーバー/クライアント処理の切り替え

## データベース設計

### 主要テーブル

- **users**: ユーザー情報（ID、氏名、組織、役職）
- **authuser**: 認証情報（ユーザーID、パスワードハッシュ）
- **organization**: 組織マスタ
- **schedule_type**: 勤怠種別マスタ（出社、在宅、有給など）
- **regular_schedule**: 定期スケジュール（曜日指定）
- **irregular_schedule**: 非定期スケジュール（日付指定）
- **default_schedule**: デフォルトスケジュール
- **common_*_schedule**: 共通スケジュール（全社員適用）

**スケジュール優先度**: 非定期 > 定期 > デフォルト > 共通

## API仕様

詳細は `openapi '3.0.yml` を参照してください。

### 主要エンドポイント

- `POST /api/login`: ログイン
- `GET /api/schedule`: スケジュール一覧取得（週次/月次）
- `GET /api/irregularSchedule`: 非定期スケジュール取得
- `POST /api/irregularSchedule`: 非定期スケジュール登録
- `PUT /api/irregularSchedule`: 非定期スケジュール更新
- `DELETE /api/irregularSchedule/{id}`: 非定期スケジュール削除
- 定期・デフォルトスケジュールも同様のCRUD操作が可能
- `GET /api/organizations`: 組織一覧取得
- `GET /api/workTypes`: 勤怠種別一覧取得

## セットアップ

### 前提条件

- Docker

### バックエンド起動

```bash
cd back/.devcontainer
docker compose up -d
docker compose exec WSMSA-back bash
gradle bootRun
```

`http://localhost:8080`でAPIサーバーが、
`localhost:5432`でDBが稼働します。

### フロントエンド起動

```bash
cd front/.devcontainer
docker compose up -d
docker compose exec WSMSA-front bash
npm install
npm run build
npm run start
```

`http://localhost:3000`でアプリケーションにアクセスできます。
ログイン画面では　ユーザー名'00001'パスワード'password'で仮ユーザとしてログインが可能です。

### データベース初期化

`application.yml`の設定により、アプリケーション起動時に以下が実行されます：
- テーブル作成（`schema/*.sql`）
- 初期データ投入（`data/*.sql`）

## 今後の課題/反省点（TODO）

※本項目は未実装・改善点の整理であり、現状動作はします。

### BACK

- Java record の compact constructor で発生する DomainException が
  Spring MVC の内部処理によりラップされ、
  意図した例外ハンドリングが行えないケースがある。
  現状はやむなく許容しているが、設計上の問題であるため、
  例外ハンドリングの整理、もしくは
  DomainException ではなく MethodArgumentNotValidException とするなどで見直しを今後検討する。

- スケジュール検索時に、
  ログインユーザーのスケジュール一覧取得と検索処理を
  同一 Controller メソッドで行っているが、
  責務分離の観点から分割するべきである。

- エラーレスポンスが message のみで構成されており、
  フロント側での制御が困難である。
  error code や種別を含めたレスポンス設計を
  見直す必要がある。

- 管理者機能が存在しない点は、
  運用および利便性の観点から課題である。

- DTO に関しては、フロントで何が必要かの取捨選択が不十分であり、
  フロント側で無用の処理を必要とした。

- CREATE/DELETE で戻り値に id が必要であることに思い至らず、
  FRONT 作成中に急遽追加したため、かなり汚い。そもそも RETURNING を使わないほうがよい気もする。
  id を返すのであれば UPDATE と INSERT のレコードは同じものにできた。

### FRONT

- カレンダー画面において、
  モーダルを用いた予定の登録・更新操作は
  UX 向上のために必須と考えられるため、
  今後実装する必要がある。

- 管理者画面での共通予定管理機能については、
  利便性向上の観点から実装が望ましい。
  本項目は BACK 側とも共通する課題である。

- usecase におけるクライアント／サーバーでの使い分けについて、
  現状は設計上かなり無理があり、
  好ましい構成とは言えないため、
  見直しが必須である。

- URL に検索条件（クエリパラメータ）が反映されておらず、
  画面の再読み込みや共有の観点から利便性が低い。
  今後の改善課題である。

- エラーメッセージをデフォルトの alert 機能で表示しているが、
  UI/UX の観点から好ましいとは言えない。
  表示方法およびメッセージ内容の設計について、
  見直しが必要である。

- CSS は別フォルダにまとめたほうがよいだろう。

### DB

- DB 設計を十分に検討しないまま実装を進めており、
  テーブル間の依存関係などの設計が不十分である。

- index 等DB自体に関する知識不足もあり、
  DB 最適化や SQL の検討が十分に行えていない。

- 共通予定について、
  そもそも DB に永続化すべき設計であったのか、
  再検討の余地がある。

### 全体

- 本プロジェクトはデプロイまでには至っておらず、
  システム構成やインフラ要件については未定義である。
  コードについても開発途中の状態である。

- 非機能要件およびセキュリティ設計については、
  作者自身の実務経験不足もあり、
  十分な検討が行えていない。
  今後の課題である。

- ログ出力や運用を想定した設計についても、
  知識不足等により十分な検討が行えていない。

- GitHub Actions 等の CI/CD 環境については、
  今回は導入に至らなかった。
