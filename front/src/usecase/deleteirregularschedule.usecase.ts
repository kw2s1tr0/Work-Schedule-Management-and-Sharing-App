import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';

export async function DeleteIrregularScheduleUsecase(
  id: string,
  type: ServerOrClientEnum,
): Promise<string> {
  return await deleteSchedule(id, type);
}

async function deleteSchedule(
  id: string,
  type: ServerOrClientEnum,
): Promise<string> {
  const response = await fetcher(
    `/api/irregularSchedule/${id}`,
    MethodEnum.DELETE,
    type,
  );

  const data = await response.json();

  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  return data;
}
