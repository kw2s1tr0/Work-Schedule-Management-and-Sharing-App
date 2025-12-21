import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { OrganizationRes } from '@/type/res/organization.res';
import { OrganizationDTO } from '@/type/dto/organization.dto';

export async function GetOrganizationUsecase(type: ServerOrClientEnum, cookie?: string): Promise<OrganizationDTO[]> {
  const organizationResList: OrganizationRes[] = await get(type, cookie);
  const organizationDTOList: OrganizationDTO[] = toDTO(organizationResList);
  return organizationDTOList;
}

async function get(type: ServerOrClientEnum, cookie?: string): Promise<OrganizationRes[]> {
  const response = await fetcher(`/api/organizations`, MethodEnum.GET, type, undefined, cookie);

  const data = await response.json();

  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  const organizationResList: OrganizationRes[] = data as OrganizationRes[];

  return organizationResList;
}

function toDTO(organizationResList: OrganizationRes[]): OrganizationDTO[] {
  const organizationDTOList: OrganizationDTO[] = organizationResList.map(
    (organizationRes) => {
      const organizationDTO: OrganizationDTO = {
        organizationCode: organizationRes.organizationCode,
        organizationName: organizationRes.organizationName,
      };
      return organizationDTO;
    }
  );
  return organizationDTOList;
}
