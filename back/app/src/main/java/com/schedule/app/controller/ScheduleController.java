package com.schedule.app.controller;

import com.schedule.app.applicationservice.DeleteDefaultScheduleService;
import com.schedule.app.applicationservice.DeleteIrregularScheduleService;
import com.schedule.app.applicationservice.DeleteRegularScheduleService;
import com.schedule.app.applicationservice.GetDefaultScheduleService;
import com.schedule.app.applicationservice.GetIrregularScheduleService;
import com.schedule.app.applicationservice.GetOrganizationService;
import com.schedule.app.applicationservice.GetRegularScheduleService;
import com.schedule.app.applicationservice.GetScheduleService;
import com.schedule.app.applicationservice.GetWorkTypeService;
import com.schedule.app.applicationservice.PostDefaultScheduleService;
import com.schedule.app.applicationservice.PostIrregularScheduleService;
import com.schedule.app.applicationservice.PostRegularScheduleService;
import com.schedule.app.applicationservice.PutDefaultScheduleService;
import com.schedule.app.applicationservice.PutIrregularScheduleService;
import com.schedule.app.applicationservice.PutRegularScheduleService;
import com.schedule.app.dto.DefaultScheduleDTO;
import com.schedule.app.dto.IrregularScheduleDTO;
import com.schedule.app.dto.OrganizationDTO;
import com.schedule.app.dto.RegularScheduleDTO;
import com.schedule.app.dto.UserDTO;
import com.schedule.app.dto.WorkTypeDTO;
import com.schedule.app.form.DefaultScheduleInsertForm;
import com.schedule.app.form.DefaultScheduleUpdateForm;
import com.schedule.app.form.IrregularScheduleInsertForm;
import com.schedule.app.form.IrregularScheduleUpdateForm;
import com.schedule.app.form.RegularScheduleInsertForm;
import com.schedule.app.form.RegularScheduleUpdateForm;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.security.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ScheduleController {

  private final GetScheduleService scheduleService;
  private final GetIrregularScheduleService irregularScheduleService;
  private final GetRegularScheduleService regularScheduleService;
  private final GetDefaultScheduleService defaultScheduleService;
  private final PostIrregularScheduleService postIrregularScheduleService;
  private final PostRegularScheduleService postRegularScheduleService;
  private final PostDefaultScheduleService postDefaultScheduleService;
  private final PutIrregularScheduleService patchIrregularScheduleService;
  private final PutRegularScheduleService patchRegularScheduleService;
  private final PutDefaultScheduleService patchDefaultScheduleService;
  private final DeleteIrregularScheduleService deleteIrregularScheduleService;
  private final DeleteRegularScheduleService deleteRegularScheduleService;
  private final DeleteDefaultScheduleService deleteDefaultScheduleService;
  private final GetOrganizationService organizationService;
  private final GetWorkTypeService workTypeService;

  /**
   * スケジュール検索 ユーザーごとのスケジュールリストを検索条件に基づき取得する
   *
   * @param form スケジュール検索条件
   * @return 条件に一致したユーザーDTOリスト(各ユーザーはスケジュールDTOリスト内包)
   */
  @GetMapping("/api/schedule")
  public List<UserDTO> getSchedule(@Valid ScheduleSearchForm form) {
    List<UserDTO> userDTOs = scheduleService.scheduleSearchService(form);
    return userDTOs;
  }

  /**
   * イレギュラースケジュール取得
   *
   * @param form 画面入力フォーム
   * @param userDetails 認証ユーザー情報
   * @return イレギュラースケジュールDTOリスト
   */
  @GetMapping("/api/irregularSchedule")
  public List<IrregularScheduleDTO> getIrregularSchedule(
      @Valid SingleScheduleSearchForm form,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    List<IrregularScheduleDTO> userDTOs =
        irregularScheduleService.irregularScheduleSearchService(form, userId);
    return userDTOs;
  }

  /**
   * レギュラースケジュール取得
   *
   * @param form 画面入力フォーム
   * @param userDetails 認証ユーザー情報
   * @return レギュラースケジュールDTOリスト
   */
  @GetMapping("/api/regularSchedule")
  public List<RegularScheduleDTO> getRegularSchedule(
      @Valid SingleScheduleSearchForm form,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    List<RegularScheduleDTO> regularSchedules =
        regularScheduleService.regularScheduleSearchService(form, userId);
    return regularSchedules;
  }

  /**
   * デフォルトスケジュール取得
   *
   * @param form 画面入力フォーム
   * @param userDetails 認証ユーザー情報
   * @return デフォルトスケジュールDTOリスト
   */
  @GetMapping("/api/defaultSchedule")
  public List<DefaultScheduleDTO> getDefaultSchedule(
      @Valid SingleScheduleSearchForm form,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    List<DefaultScheduleDTO> defaultSchedules =
        defaultScheduleService.defaultScheduleSearchService(form, userId);
    return defaultSchedules;
  }

  /**
   * イレギュラースケジュール登録
   *
   * @param form 画面入力フォーム
   * @param userDetails 認証ユーザー情報
   * @return ResponseEntity<Integer> 生成されたID
   */
  @PostMapping("/api/irregularSchedule")
  public ResponseEntity<Integer> postIrregularSchedule(
      @Valid @RequestBody IrregularScheduleInsertForm form,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    Integer id = postIrregularScheduleService.postIrregularScheduleService(form, userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  /**
   * レギュラースケジュール登録
   *
   * @param form 画面入力フォーム
   * @param userDetails 認証ユーザー情報
   * @return ResponseEntity<Integer> 生成されたID
   */
  @PostMapping("/api/regularSchedule")
  public ResponseEntity<Integer> postRegularSchedule(
      @Valid @RequestBody RegularScheduleInsertForm form,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    Integer id = postRegularScheduleService.postRegularScheduleService(form, userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  /**
   * デフォルトスケジュール登録
   *
   * @param form 画面入力フォーム
   * @param userDetails 認証ユーザー情報
   * @return ResponseEntity<Integer> 生成されたID
   */
  @PostMapping("/api/defaultSchedule")
  public ResponseEntity<Integer> postDefaultSchedule(
      @Valid @RequestBody DefaultScheduleInsertForm form,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    Integer id = postDefaultScheduleService.postDefaultScheduleService(form, userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  /**
   * イレギュラースケジュール更新
   *
   * @param form 画面入力フォーム
   * @param userDetails 認証ユーザー情報
   * @return ResponseEntity<Void> 204 NO CONTENT
   */
  @PutMapping("/api/irregularSchedule")
  public ResponseEntity<Void> patchIrregularSchedule(
      @Valid @RequestBody IrregularScheduleUpdateForm form,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    patchIrregularScheduleService.patchIrregularScheduleService(form, userId);
    return ResponseEntity.noContent().build();
  }

  /**
   * レギュラースケジュール更新
   *
   * @param form 画面入力フォーム
   * @param userDetails 認証ユーザー情報
   * @return ResponseEntity<Void> 204 NO CONTENT
   */
  @PutMapping("/api/regularSchedule")
  public ResponseEntity<Void> patchRegularSchedule(
      @Valid @RequestBody RegularScheduleUpdateForm form,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    patchRegularScheduleService.patchRegularScheduleService(form, userId);
    return ResponseEntity.noContent().build();
  }

  /**
   * デフォルトスケジュール更新
   *
   * @param form 画面入力フォーム
   * @param userDetails 認証ユーザー情報
   * @return ResponseEntity<Void> 204 NO CONTENT
   */
  @PutMapping("/api/defaultSchedule")
  public ResponseEntity<Void> patchDefaultSchedule(
      @Valid @RequestBody DefaultScheduleUpdateForm form,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    patchDefaultScheduleService.patchDefaultScheduleService(form, userId);
    return ResponseEntity.noContent().build();
  }

  /**
   * イレギュラースケジュール削除
   *
   * @param id スケジュールID
   * @param userDetails 認証ユーザー情報
   * @return ResponseEntity<Integer> 削除したID
   */
  @DeleteMapping("/api/irregularSchedule/{id}")
  public ResponseEntity<Integer> deleteIrregularSchedule(
      @PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    Integer deletedId = deleteIrregularScheduleService.deleteIrregularScheduleService(id, userId);
    return ResponseEntity.ok(deletedId);
  }

  /**
   * レギュラースケジュール削除
   *
   * @param id スケジュールID
   * @param userDetails 認証ユーザー情報
   * @return ResponseEntity<Integer> 削除したID
   */
  @DeleteMapping("/api/regularSchedule/{id}")
  public ResponseEntity<Integer> deleteRegularSchedule(
      @PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    Integer deletedId = deleteRegularScheduleService.deleteRegularScheduleService(id, userId);
    return ResponseEntity.ok(deletedId);
  }

  /**
   * デフォルトスケジュール削除
   *
   * @param id スケジュールID
   * @param userDetails 認証ユーザー情報
   * @return ResponseEntity<Integer> 削除したID
   */
  @DeleteMapping("/api/defaultSchedule/{id}")
  public ResponseEntity<Integer> deleteDefaultSchedule(
      @PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    Integer deletedId = deleteDefaultScheduleService.deleteDefaultScheduleService(id, userId);
    return ResponseEntity.ok(deletedId);
  }

  /**
   * 組織一覧取得 全ての組織のコードと名称を取得する
   *
   * @return 組織DTOリスト
   */
  @GetMapping("/api/organizations")
  public List<OrganizationDTO> getOrganizations() {
    return organizationService.getOrganizationList();
  }

  /**
   * 勤怠種別一覧取得 全ての勤怠種別（休日を除く）のID、名称、カラーコードを取得する
   *
   * @return 勤怠種別DTOリスト
   */
  @GetMapping("/api/workTypes")
  public List<WorkTypeDTO> getWorkTypes() {
    return workTypeService.getWorkTypeList();
  }

  /**
   * ログインしているか確認用
   *
   * @return ResponseEntity<Void> 200 OK
   */
  @GetMapping("/api/session")
  public ResponseEntity<Void> getLoginInfo() {
    return ResponseEntity.ok().build();
  }

  /**
   * 認証ユーザー情報取得
   *
   * @param userDetails 認証ユーザー情報
   * @return ユーザーID
   */
  @GetMapping("/api/me")
  public ResponseEntity<String> getUserInfo(
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    String userId = userDetails.getUsername();
    return ResponseEntity.ok(userId);
  }
}
