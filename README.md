# 🏪 Convenience-Store 🏪


---

## 📔 패키지 및 클래스 설명

<div align="center">
<table>
<tr>
<th align="center">Package</th>
<th align="center">Class</th>
<th align="center">Description</th>
</tr>

<tr>
<td rowspan="1"><b>⚙️  config</b></td>
<td><b> AppConfig</b></td>
<td>  애플리케이션의 전반적인 설정과 의존성 주입을 관리하는 구성 클래스 </td>
</tr>

<tr><td colspan="3"></td></tr>

<tr>
<td rowspan="1"><b> 🔢️  constants</b></td>
<td><b> Answer </b></td>
<td>  사용자의 응답 관련 상수를 보관하는 enum 클래스 </td>
</tr>




<tr><td colspan="3"></td></tr>



<tr>
<td rowspan="2"><b>🕹&nbsp;&nbsp;controller</b></td>
<td><b> OrderController </b></td>
<td> 받은 주문을 처리하는 과정을 동작하는 컨트롤러 클래스 </td>
</tr>

<tr>
<td><b> StoreController</b></td>
<td> 편의점 로직 전체를 동작하는 컨트롤러 클래스 </td>
</tr>

<tr><td colspan="3"></td></tr>

<tr>
<td rowspan="3"><b>💻  domain.builder</b></td>
<td><b> InventoryBuilder  </b></td>
<td> Inventory 빌더 클래스 </td>
</tr>
<tr>
<td><b> PromotionsBuilder  </b></td>
<td> Promotions 빌더 클래스  </td>
</tr>

<tr>
<td><b> OrdersBuilder</b></td>
<td> Orders 빌더 클래스 </td>
</tr>


<tr>
<td rowspan="6"><b>💻  domain.model</b></td>
<td><b> StoreProduct </b></td>
<td> 한 가지 상품의 재고 관련 로직을 처리하는 클래스 </td>
</tr>
<tr>
<td><b> StoreInventory</b></td>
<td>  StoreProduct 일급 컬렉션. 전체 재고 관리를 하는 클레스 </td>
</tr>

<tr>
<td><b> StoreOrder </b></td>
<td> 주문에서 한 가지 상품 관련 로직을 처리하는 클래스 </td>
</tr>
<tr>
<td><b> StoreOrders </b></td>
<td> StoreOrder 객체 일급 컬렉션. 전체 주문에 대한 로직을 처리하는 클래스   </td>
</tr>



<tr>
<td><b> StorePromotion </b></td>
<td> 프로모션 관련 로직을 처리하는 클래스 </td>
</tr>

<tr>
<td><b> StorePromotions </b></td>
<td> StorePromotion 객체  일급 컬렉션 </td>
</tr>




<tr>
<td rowspan="2"><b>💻  domain.processDiscount</b></td>
<td><b> MoneyCalculator</b></td>
<td> 다양한 금액관련 계산을 담당하는 클래스 </td>
</tr>

<tr>
<td><b> StoreMembership</b></td>
<td> 멤버십에 의한 로직을 담당하는 클래스 </td>
</tr>

<tr>
<td rowspan="3"><b>💻  domain.repository</b></td>
<td><b> InventoryRepository</b></td>
<td>  Inventory 객체를 저장하고 조회하는 기능을 제공하는 저장소 클래스 </td>
</tr>

<tr>
<td><b> OrdersRepository</b></td>
<td>  Orders 객체를 저장하고 조회하는 기능을 제공하는 저장소 클래스 </td>
</tr>

<tr>
<td><b> PromotionsRepository </b></td>
<td>  Promotions 객체를 저장하고 조회하는 기능을 제공하는 저장소 클래스 </td>
</tr>


<tr>
<td rowspan="1"><b>💻  domain.storeOpen</b></td>
<td><b> FileReader</b></td>
<td> 프로그램 시작시 파일로부터 정보를 받아오는 클래스 </td>
</tr>


<tr><td colspan="3"></td></tr>

<tr>
<td rowspan="5"><b>✉️&nbsp;&nbsp;dto</b></td>
<td><b> DecisionNeededDTO</b></td>
<td> 프로모션 혜탹 추가 여부를 결정해야하는 리스트를 리턴하는 DTO 레코드 </td>
</tr>


<tr>
<td><b> FinalOrderDTO </b></td>
<td> 확정된 구매 정보를 리턴하는 DTO 레코드 </td>
</tr>

<tr>
<td><b> FinalPromotionDTO </b></td>
<td> 확정된 프로모션 정보를 리턴하는 DTO 레코드</td>
</tr>

<tr>
<td><b> InsufficientStockDTO </b></td>
<td> 프로모션 재고가 부족한 리스트를 리턴하는 DTO 레코드 </td>
</tr>


<tr>
<td><b> FormattedStockDTO.java </b></td>
<td> 포매팅된 재고 정보를 리턴하는 DTO 레코드</td>
</tr>

<tr><td colspan="3"></td></tr>

<tr>
<td rowspan="2"><b>🚫&nbsp;&nbsp;exception</b></td>
<td><b> ErrorMessage </b></td>
<td> 예외 발생 시 사용되는 에러 메세지 Enum 클래스</td>
</tr>

<tr>
<td><b> EntityNotFoundException </b></td>
<td> 특정 엔티티가 존재하지 않을 때 발생하는 예외를 위한 커스텀 예외 클래스</td>
</tr>

<tr><td colspan="3"></td></tr>

<tr>
<td rowspan="4"><b>💼&nbsp;&nbsp;service</b></td>
<td><b> PrepareOrderServiceImpl  </b></td>
<td> 주문 받기 전 해야하는 로직을 담당하는 서비스 레이어 클래스</td>
</tr>

<tr>
<td><b> ReceiveOrderServiceImpl  </b></td>
<td> 입력값에 주문 목록을 생성하는 로직을 담당하는 서비스 레이어 클래스</td>
</tr>

<tr>
<td><b> ProcessOrderServiceImpl  </b></td>
<td> 입력값에 따라 주문 목록을 수정하는 로직을 담당하는 서비스 레이어 클래스 </td>
</tr>

<tr>
<td><b> FinishOrderServiceImpl </b></td>
<td> 영수증을 발행하고 그 결과를 반영, 재구매 의사를 묻는 서비스 레이어 클래스 </td>
</tr>




<tr><td colspan="3"></td></tr>

<tr>
<td rowspan="5"><b>🌟&nbsp;&nbsp;utils</b></td>
<td><b> FormattedStockDTOMapper </b></td>
<td> FormattedStockDTO의 Mapper 클래스</td>
</tr>

<tr>
<td><b> MethodPattern.java </b></td>
<td> 반복되는 패턴을 재사용할 수 있게 도와주는 헬퍼 클래스</td>
</tr>

<tr>
<td><b> LocalDateParser.java </b></td>
<td> 유효성 검증 후 데이터를 LocalDate 자료형으로 파싱하는 Parser 클래스 </td>
</tr>

<tr>
<td><b> PositiveIntParser.java </b></td>
<td> 특정 값이 양의 정수가 될 수 있는 지 확인 후 파싱하는 Parser 클래스</td>
</tr>

<tr>
<td><b> ResponseValidator.java </b></td>
<td> 사용자가 입력한 응답값이 유효한 지 검증하는 validator 클래스</td>
</tr>


<tr><td colspan="3"></td></tr>

<tr>
<td rowspan="2"><b>💬&nbsp;&nbsp;view</b></td>
<td><b> ConsoleInputView </b></td>
<td> 사용자에게 입력을 받는 기능을 담당하는 클래스 </td>
</tr>


<tr>
<td><b> ConsoleOutputView </b></td>
<td> 사용자에게 응답을 출력하는 클래스</td>
</tr>



</table>
</div>

*편의를 위해 Interface는 제외하였습니다.

---

## 🌊 프로그램 플로우 및 구현한 기능


- [x] **1. 구현에 필요한 상품목록과 행사 목록을 파일 입출력을 통해 불러온다.**
  - [x] ```src/main/resources/products.md```과 ```src/main/resources/promotions.md``` 파일을 읽어드린다
  - [x] 프로모션 정보 ( 기한, N+1.. ) 를 얻어 저장한다.
    - [x] 잘못된 정보 양식이 들어오는 경우 오류를 발생시킨다.
  - [x] 불러온 값을 통해 재고, 프로모션 재고를 얻어 저장한다.
    - [x] 잘못된 정보 양식이 들어오는 경우 오류를 발생시킨다.

<br>

- [x] **2. 상품 재고와 행사 여부를 안내문구와 함께 출력한다.**
  - [x] 먼저 안내 문구를 출력한다
  - [x] 재고 목록을 출력한다. 
    - [x] 각각의 상품마다 "- 상품명 금액 보유갯수 프로모션" 형태로 출력한다
    - [x] 상품 가격은 0 세 개 마다 ","를 붙여 출력한다 
    - [x] 상품 수량이 0개인 경우 "재고 없음" 이라고 출력한다.
    - [x] 상품명이 동일하더라도, 프로모션 재고와 일반 재고를 구분하여 출력한다.

<br>

- [x] **3. 안내 후 구매할 상품과 수량을 입력 받는다.**
  - [x] 안내 문구를 출력한다.
  - [x] 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어서 쉼표(,)로 구분한다
    - ex ) [콜라-10],[사이다-3]
  - [x] 양식에 맞지 않는 입력값의 경우 오류를 발생시키고  다시 입력을 받는다. 

<br>

- [x] **4. 각 상품의 재고 수량을 고려하여 결제 가능여부를 확인 및 구매 목록을 만든다.**
  - [x] 결제 불가능한 입력값이 오는 경우 오류 발생 후 다시 입력받는다.
    - [x] 존재하지 않는 상품을 입력한 경우 오류를 발생시킨다.
    - [x] 각 상품의 “프로모션 재고 수량 + 재고 수량”보다 결제 요청한 값이 크면 오류를 발생시킨다.
  - [x] 프로모션 혜택이 있는 경우, 프로모션 기간을 확인하여 할인이 적용되는 지 확인한다.
    - [x] 적용이 되는 경우 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우 일반 재고를 사용한다. 
    - [x] 적용이 안되는 경우 일반 재고를 우선적으로 차감하며, 프로모션 재고가 부족한 경우 일반 재고를 사용한다.
  - [x] 프로모션 적용 여부까지 포함한 구매 리스트를 생성한다.

<br>

 - [x] **로직에 의해서 5번과 6번은 같은 상품에 대해 적용되지 않는다.**
   - [x] 5번의 대상이 된 상품은 6번의 대상에서 제외된다.

<br>

- [x] **5. 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.** 
  - [x] 안내 문구를 출력한다.
  - [x] 입력값에 따라 다른 처리를 진행한다. 
    - [x] Y: 증정 받을 수 있는 상품을 추가한다. / N: 증정 받을 수 있는 상품을 추가하지 않는다.
    - [x] Y 와 N 이외의 입력값은 오류 발생 후 다시 입력받는다.
  - [x] 로직 종료 시 7번 로직으로 넘어간다.


<br>

- [x] **6. 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할 지, 여부를 입력받는다.**
  - [x] 안내 문구를 출력한다.
  - [x] 입력값에 따라 다른 처리를 진행한다.
    - [x] Y: 일부 수량에 대해 정가로 결제한다. 즉 그대로 유지한다. / N: 정가로 결제해야하는 수량만큼 구매 목록에서 제거한다.
    - [x] Y 와 N 이외의 입력값은 오류 발생 후 다시 입력받는다.

<br>

- [x] **7. 멤버십 할인 적용 여부를 입력 받는다**
  - [x] 안내 문구를 출력한다.
  - [x] 입력값에 따라 다른 처리를 진행한다. 
    - [x] Y: 멤버십 할인을 적용한다. / N: 멤버십 할인을 적용하지 않는다.
  - [x] 멤버십 할인을 하는 경우, 로직에 따라 할인을 진행한다. 
    - [x] 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다. 
    - [x] 프로모션 미적용 금액의 30%를 할인받는다. 
    - [x] 단, 멤버십 할인의 최대 한도는 8,000원이다.


<br>

- [x] **8. 구매금액를 계산한다.**
  - [x] 프로모션 및 할인 적용 이전 구매한 총 수량과 총 금액을 통해 총구매액을 구한다.
  - [x] 이후 프로모션에 의해서 무엇이 증정되었는 지 데이터를 불러오고, 그 증정된 상품들의 가격의 합을 통해 행사할인을 구한다. 
  - [x] 이후 멤버십ㅇ 의해 추가로 할인된 금액안 멤버십할인을 구한다
  - [x] “내실돈” = “총구매액”  - “행사할인” - “멤버십할인” 를 통해 최종 결제 금액을 구한다

<br>


- [x] **9. 계산한 결과를 영수증 형태로 출력한다.**
  - [x] 이때 구매 결과를 재고에 반영한다.
  - [x] 8번을 통해 구한 값들을 양식에 맞춰서 출력한다.


<br>


- [x] **10. 추가 구매 여부를 묻는다.**
  - [x] Y: 2번로직으로 돌아간다. 이때 이전 구매 결과가 반영된 재고가 출력되어야 한다.
  - [x] N: 프로그램을 종료한다.


---


## ✨ 이번에 도입 새로 도입한 것.

 - **Repository 클래스**: 서비스에서 도메인 객체를 필드로 들고 있는 것이 아니라 Repository를 통해 접근하는 방식으로 구헌
 - **커스텀 예외 클래스**: Repository에 저장된 객체가 없는 경우를 명시하기 위한 커스텀 예외 클래스 도입
 - **Helper 클래스**: 반복되는 메서드를 유틸 클래스로 만들어 재사용성 확보