<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<main>
	<!--================Single Product Area =================-->
	<div class="product_image_area">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-lg-8">
					<div class="product_img_slide owl-carousel">
						<div class="single_product_img">
							<img src="assets/img/gallery/aaa.jpg" alt="#" class="img-fluid">
						</div>
						<div class="single_product_img">
							<img src="assets/img/gallery/bbb.jpg" alt="#" class="img-fluid">
						</div>
						<div class="single_product_img">
							<img src="assets/img/gallery/ccc.jpg" alt="#" class="img-fluid">
						</div>
					</div>
				</div>
				<div class="col-lg-12">
					<div class="single_product_text text-center">
						<h3>${recipe.title }</h3>
						<p>${recipe.content }</p>
						<form action="cart.do" method="post">
							<div class="card_area">
								<div class="container" style="width: 800px">
									<table class="table">
										<thead>
											<tr>
												<td align="right"><b>재료명</b></td>
												<td><b>수량</b></td>
												<td align="left"><b>단위</b></td>
										</thead>
										<tbody>
	
												<tr>
													<td align="right" width=30%>${matOne.mat_nm }</td> <!-- 재료명 -->
													<td align="center" width=40%>
														<div class="product_count d-inline-block">
															<span id="decrement" class="product_count_item inumber-decrement"> 
															<i class="ti-minus"></i></span> <input id="mat_count" name="mat_count"
																class="product_count_item input-number" type="text"
																value="1" min="0" max="10"> <span
																id="increment"
																class="product_count_item number-increment"> <i
																class="ti-plus"></i>
															</span>
														</div>
														<input type="hidden" name="material" value="${material.mat_no }"/>
													</td>
													<td align="left" width=30%>${matOne.mat_unit }</td> <!-- 단위 -->
												</tr>
											
											

											<tr>
												<c:if test="${not empty session_no }"> <!-- add cart -->
													<td colspan="3">
														<div class="add_to_cart">
															<button type="submit" value="add to cart" class="btn_3">add
																to cart</button>
															<input type="hidden" name="session_id" value="${session_id }"/>
															<input type="hidden" name="no" value="${session_no }"/>
															
														</div>
													</td>
												</c:if>
												<c:if test="${empty session_no }"> <!--로그인 안되어있을때 add cart  -->
													<td colspan="3">
														<div class="add_to_cart">
															<button type="button" id="login" value="${matOne.mat_no }" class="btn_3">로그인
															</button>
															
														</div>
													</td>
												</c:if>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--================End Single Product Area =================-->
	<!-- subscribe part here -->
	<section class="subscribe_part section_padding">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-lg-8">
					<div class="subscribe_part_content">
						<h2>Get promotions & updates!</h2>
						<p>Seamlessly empower fully researched growth strategies and
							interoperable internal or “organic” sources credibly innovate
							granular internal .</p>
						<div class="subscribe_form">
							<input type="email" placeholder="Enter your mail"> <a
								href="#" class="btn_1">Subscribe</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- subscribe part end -->
</main>
<script type="text/javascript">
	for(let i=1;i<4;i++){
		$('#increment').on('click', function() {
			var value = $('#mat_count').val();
			var min = 0;
			var max = 10;
			parseInt(value);
			value++;
			if (value <= max) {
				$('#mat_count').val(value);
			} else {
				alert("최대수량 초과");
			}
	
		});
		$('#decrement').on('click', function() {
			var value = $('#mat_count').val();
			var min = 0;
			var max = 10;
			parseInt(value);
			value--;
			if (value >= min) {
				$('#mat_count').val(value);
			} else {
				alert("최소값입니다.");
			}
		});
	}

	$('#login').on('click',function(){
		var value=$('#login').val();
		location.href="loginForm.do?no="+value;
	});
</script>
<%@ include file="../include/footer.jsp"%>