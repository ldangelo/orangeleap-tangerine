<form>
	<jsp:include page="personHeader.jsp" />
	<br />
	<h4 class="formSectionHeader">
		Contact Information
	</h4>
	<div class="columns">
		<div class="column">
			<ul class="formFields width375" id="orderInformation">
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Account Number
					</label>

					<input value="15001" readonly="readonly" size="16" class="field text" name="Field1" id="Field1" />

				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Title
					</label>

					<select>
						<option value="blank">
							&nbsp;
						</option>
						<option value="mr" selected="true">
							Mr.
						</option>
						<option value="mrs">
							Mrs.
						</option>
						<option value="miss">
							Miss
						</option>
					</select>
				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						First Name
					</label>
					<input value="John" size="16" class="field text" name="Field1" id="Field1" />

				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Middle Initial
					</label>


					<input value="C" size="16" class="field text" name="Field1" id="Field1" />

				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Last Name
					</label>

					<input value="Hopkins" size="16" class="field text" name="Field1" id="Field1" />
				</li>

				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Suffix
					</label>

					<select>
						<option value="blank">
							&nbsp;
						</option>
						<option value="mr">
							Jr.
						</option>
					</select>
				</li>

				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Organization Name
					</label>

					<input value="Hopkins, Inc." size="16" class="field text" name="Field1" id="Field1" />
				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						 Email
					</label>

					<input value="johnhopkins@gmail.com" size="16" class="field text" name="Field1" id="Field1" />

				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Marital Status
					</label>

					<select>
						<option value="Unknown">Unknown</option>
						<option value="Married" selected="true">Married</option>
						<option value="Single">Single</option>
					</select>
				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Spouse First Name
					</label>

					<input value="Sarah" size="16" class="field text" name="Field1" id="Field1" />
				</li>
				<li class="clear"></li>
			</ul>
		</div>
		<div class="column">
			<ul class="formFields width375" id="orderInformation">
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Street Address 1
					</label>

					<input value="1500 Main St." size="16" class="field text" name="Field1" id="Field1" />

				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Street Address 2
					</label>

					<input value="Suite 201" size="16" class="field text" name="Field1" id="Field1" />
				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Country
					</label>
					<select>
						<option value="usa">
							USA
						</option>
						<option value="canada">
							Canada
						</option>
					</select>

				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Zip Code
					</label>

					<input value="75001" size="16" class="field text" name="Field1" id="Field1" />
				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						City
					</label>


					<input value="Dallas" size="16" class="field text" name="Field1" id="Field1" />

				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						State
					</label>

					<input value="TX" size="16" class="field text" name="Field1" id="Field1" />
				</li>

				<li class="side">
					<label for="Field1" id="title0" class="desc">
						 Home Phone
					</label>

					<input value="214-555-1234" size="16" class="field text" name="Field1" id="Field1" />

				</li>
				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Work Phone
					</label>

					<input value="214-555-5678" size="16" class="field text" name="Field1" id="Field1" />
				</li>

				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Mobile Phone
					</label>

					<input value="469-555-1234" size="16" class="field text" name="Field1" id="Field1" />
				</li>

				<li class="side">
					<label for="Field1" id="title0" class="desc">
						Primary Phone
					</label>

					<select>
						<option value="Unknown">Home</option>
						<option value="Married">Work</option>
						<option value="Single">Mobile</option>
					</select>
				</li>
				<li class="clear"></li>
			</ul>
		</div>
		<div class="clearColumns"></div>
	</div>
	<br />
	<div class="formButtonFooter">
	<input type="submit" value="Save" />
	<input type="submit" value="Cancel" />
	</div>

</form>