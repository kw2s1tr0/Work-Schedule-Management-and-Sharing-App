package com.schedule.app.applicationservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.GetOrganizationService;
import com.schedule.app.dto.OrganizationDTO;
import com.schedule.app.record.output.OrganizationRecord;
import com.schedule.app.repository.OrganizationSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GetOrganizationServiceImpl implements GetOrganizationService {

    private final OrganizationSearchMapper organizationSearchMapper;

    /**
     * 組織一覧を取得する
     * 
     * @return 組織DTOリスト
     */
    @Override
    public List<OrganizationDTO> getOrganizationList() {
        List<OrganizationRecord> records = readOrganizationList();
        List<OrganizationDTO> dtos = toOrganizationDTOList(records);
        return dtos;
    }

    /**
     * 組織一覧を読み取る
     * 
     * @return 組織レコードリスト
     */
    @Override
    public List<OrganizationRecord> readOrganizationList() {
        List<OrganizationRecord> records = organizationSearchMapper.readOrganizationRecord();
        return records;
    }

    /**
     * 組織レコードをDTOリストに変換する
     * 
     * @param records 組織レコードリスト
     * @return 組織DTOリスト
     */
    @Override
    public List<OrganizationDTO> toOrganizationDTOList(List<OrganizationRecord> records) {
        List<OrganizationDTO> dtos = new ArrayList<>();
        for (OrganizationRecord record : records) {
            OrganizationDTO dto = OrganizationDTO.builder()
                    .organizationCode(record.getOrganizationCode())
                    .organizationName(record.getOrganizationName())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }
}
