import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { workTypeRes } from '@/type/res/worktype.res';
import { WorkTypeDTO } from '@/type/dto/worktype.dto';

export async function GetWorkTypeUsecase(
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<WorkTypeDTO[]> {
  const workTypeResList: workTypeRes[] = await get(type, cookie);
  const workTypeDTOList: WorkTypeDTO[] = toDTO(workTypeResList);
  return workTypeDTOList;
}

async function get(
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<workTypeRes[]> {
  const response = await fetcher(
    `/api/workTypes`,
    MethodEnum.GET,
    type,
    undefined,
    cookie,
  );

  const data = await response.json();

  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  const workTypeResList: workTypeRes[] = data as workTypeRes[];

  return workTypeResList;
}

function toDTO(workTypeResList: workTypeRes[]): WorkTypeDTO[] {
  const workTypeDTOList: WorkTypeDTO[] = workTypeResList.map((workTypeRes) => {
    const workTypeDTO: WorkTypeDTO = {
      id: workTypeRes.id.toString(),
      workTypeName: workTypeRes.workTypeName,
      workTypeColor: workTypeRes.workTypeColor,
    };
    return workTypeDTO;
  });
  return workTypeDTOList;
}
