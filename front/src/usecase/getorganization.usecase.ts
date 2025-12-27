import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { OrganizationRes } from '@/type/res/organization.res';
import { OrganizationDTO } from '@/type/dto/organization.dto';

/**
 * 組織取得ユースケース
 * @param type サーバーorクライアント
 * @param cookie クッキー
 * @returns 組織DTO配列
 */
export async function GetOrganizationUsecase(
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<OrganizationDTO[]> {
  const organizationResList: OrganizationRes[] = await get(type, cookie);
  const organizationDTOList: OrganizationDTO[] = toDTO(organizationResList);
  return organizationDTOList;
}

/**
 * 組織取得処理
 * @param type サーバーorクライアント
 * @param cookie クッキー
 * @returns 組織レスポンス配列
 */
async function get(
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<OrganizationRes[]> {
  const response = await fetcher(
    `/api/organizations`,
    MethodEnum.GET,
    type,
    undefined,
    cookie,
  );

  const data = await response.json();

  // エラーチェック
  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  const organizationResList: OrganizationRes[] = data as OrganizationRes[];

  return organizationResList;
}

/** * 組織レスポンスをDTOに変換
 * @param organizationResList 組織レスポンス配列
 * @returns 組織DTO配列
 */
function toDTO(organizationResList: OrganizationRes[]): OrganizationDTO[] {
  const organizationDTOList: OrganizationDTO[] = organizationResList.map(
    (organizationRes) => {
      const organizationDTO: OrganizationDTO = {
        organizationCode: organizationRes.organizationCode,
        organizationName: organizationRes.organizationName,
      };
      return organizationDTO;
    },
  );
  return organizationDTOList;
}
