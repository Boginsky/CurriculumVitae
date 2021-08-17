<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ attribute name="selected" required="true" type="java.lang.String" %>

<ul class="nav nav-tabs" role="tablist">
	<li role="presentation" ${selected == 'profile'     ? 'class="active"' : ''}><a href="./edit">Основное</a></li>
	<li role="presentation" ${selected == 'contacts'    ? 'class="active"' : ''}><a href="./contacts">Контакты</a></li>
	<li role="presentation" ${selected == 'skills'      ? 'class="active"' : ''}><a href="./skills">Технические навыки</a></li>
	<li role="presentation" ${selected == 'practics'    ? 'class="active"' : ''}><a href="./practics">Практический опыт</a></li>
	<li role="presentation" ${selected == 'courses'	 	? 'class="active"' : ''}><a href="./courses">Курсы</a></li>
	<li role="presentation" ${selected == 'education'   ? 'class="active"' : ''}><a href="./education">Образование</a></li>
	<li role="presentation" ${selected == 'languages'   ? 'class="active"' : ''}><a href="./languages">Языки</a></li>
	<li role="presentation" ${selected == 'info'   	 	? 'class="active"' : ''}><a href="./info">Дополнительно</a></li>
</ul>