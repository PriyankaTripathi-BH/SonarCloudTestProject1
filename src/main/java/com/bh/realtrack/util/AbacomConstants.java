package com.bh.realtrack.util;

/*
 * @Author Pooja Ayre
 */
public final class AbacomConstants {

	public static final String GET_ROLE_DETAILS = "select ar.role_id, role_name from rt_app.users_v uv join rt_app.rt_adm_role ar on (uv.role_id = ar.role_id)\n"
			+ "where uv.user_sso = ? and uv.company_id = ?";

	public static final String GET_TPS_TIERS3 = "select distinct coalesce(oppty_tier_3,'Unassigned') as tps_tier_3 from rt_app.rt_abm_dm order by 1";
	public static final String GET_SUB_ORG_REGION = "select distinct (case when coalesce(primary_region_code,'Unassigned') = '' then 'Unassigned' else coalesce(primary_region_code,'Unassigned') end) as region\r\n"
			+ "from rt_app.rt_abm_dm order by 1";

	public static final String GET_SUB_ORGANIZATION = "select distinct coalesce(sub_org_map,'Unassigned') as sub_org from rt_app.rt_abm_dm order by 1";

	public static final String GET_PROCESS_HARMONIZATION_DASHBOARD = "select * from (select * from rt_app.get_phd_details() a right join\r\n"
			+ "( select distinct (extract(year from date_q))||'-Q'||(extract(quarter from date_q)) as otd_y_q from\r\n"
			+ "( SELECT d::date as date_q FROM\r\n"
			+ "(select case when ? ='current quarter' then generate_series((now())::date,now()+interval '0 day', '1 day')\r\n"
			+ "when ? ='current year' then generate_series((date_part('year',now()::date)||'-'||01||'-'||01)::date,(date_part('year',now()::date)||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='3years' then generate_series((date_part('year',now())||'-'||01||'-'||01)::date,(date_part('year',now())+2||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='next year' then generate_series((date_part('year',now())+1||'-'||01||'-'||01)::date,(date_part('year',now())+1||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='6q rolling' then generate_series((date_part('year',now()::date)||'-'||01||'-'||01)::date,(date_part('year',now()::date)||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "end) AS gs(d))a) q\r\n"
			+ "on (extract(year from eod_date::date))||'-Q'||(extract(quarter from eod_date::date))=otd_y_q) a\r\n"
			+ "where (case when ? <> '0' then (a.qmi_category) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.slot_insight_last_updated ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.slot_request_type ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.deal_budgetary_type) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.forecastexception) = any (string_to_array (?,',')) else true end )";
	
	public static final String GET_PHD_WITH_TOGGLE_OVERALL_HORIZON = "select * from rt_app.get_phd_details() a\r\n"
			+ "where (case when ? <> '0' then (a.qmi_category) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.slot_insight_last_updated ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.slot_request_type ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.deal_budgetary_type) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.forecastexception) = any (string_to_array (?,',')) else true end )"
			+ "and a.slot_insight_disconnection = 'Y'";
	
	public static final String GET_PHD_WITHOUT_TOGGLE_OVERALL_HORIZON = "select * from rt_app.get_phd_details() a\r\n"
			+ "where (case when ? <> '0' then (a.qmi_category) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.slot_insight_last_updated ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.slot_request_type ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.deal_budgetary_type) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.forecastexception) = any (string_to_array (?,',')) else true end )";
	
	public static final String GET_PROCESS_HARMONIZATION_DASHBOARD_WITH_TOGGLE = "select * from (select * from rt_app.get_phd_details() a right join\r\n"
			+ "( select distinct (extract(year from date_q))||'-Q'||(extract(quarter from date_q)) as otd_y_q from\r\n"
			+ "( SELECT d::date as date_q FROM\r\n"
			+ "(select case when ? ='current quarter' then generate_series((now())::date,now()+interval '0 day', '1 day')\r\n"
			+ "when ? ='current year' then generate_series((date_part('year',now()::date)||'-'||01||'-'||01)::date,(date_part('year',now()::date)||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='3years' then generate_series((date_part('year',now())||'-'||01||'-'||01)::date,(date_part('year',now())+2||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='next year' then generate_series((date_part('year',now())+1||'-'||01||'-'||01)::date,(date_part('year',now())+1||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='6q rolling' then generate_series((date_part('year',now()::date)||'-'||01||'-'||01)::date,(date_part('year',now()::date)||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "end) AS gs(d))a) q\r\n"
			+ "on (extract(year from eod_date::date))||'-Q'||(extract(quarter from eod_date::date))=otd_y_q) a\r\n"
			+ "where (case when ? <> '0' then (a.qmi_category) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.slot_insight_last_updated ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.slot_request_type ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.deal_budgetary_type) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (a.forecastexception) = any (string_to_array (?,',')) else true end ) "
			+ "and a.slot_insight_disconnection = 'Y'";

	public static final String GET_LAST_UPDATED_ON = "select distinct(max(insert_dt))as last_updated_on from rt_app.rt_abm_asi";

	public static final String GET_LAST_PUBLISHED_DATE = "\r\n"
			+ "select (case when ((select distinct max(published_date)::date from rt_app.rt_abm_phd_pub)  = now()::date) then\r\n"
			+ "(select distinct to_char(max(pp.published_date),'DD-MON-YYYY HH24:MI:SS') || ' GMT'  as last_published_Date\r\n"
			+ "from rt_app.rt_abm_phd_pub pp) else '' end)last_published_Date ,\r\n"
			+ "(case when ((select distinct max(published_date)::date from rt_app.rt_abm_phd_pub)  = now()::date) then\r\n"
			+ "(select concat(au.first_name,' ',au.last_name) as last_published_by from\r\n"
			+ "rt_app.rt_abm_phd_pub pp join rt_app.rt_adm_user au on (au.user_sso = pp.published_by and pp.published_date::date = now()::date)\r\n"
			+ "            group by concat(au.first_name,' ',au.last_name)) else '' end)last_published_by";

	public static final String INSERT_PROCESS_HARMONIZATION_DASHBOARD = "INSERT INTO rt_app.rt_abm_phd_pub (oppty_number, oppty_name_dm, qmi_category, eod, slot_request_type, business_release, slot_confirmation, slot_start_date, slot_insight_last_updated, slot_insight_last_published, published_by, published_date, f0_color, f0_tooltip, f1_color, f1_tooltip, f2_color, f2_tooltip, f3_color, f3_tooltip, proposed_slot_insight_tooltip, slot_insight_last_saved_color, slot_insight_last_saved_tooltip, slot_insight_to_be_published_tooltip, note, comments, oppty_amount_usd) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	public static final String SELECT_PROCESS_HARMONIZATION_DASHBOARD = "select count(*) from rt_app.rt_abm_phd_pub where published_date::date = ?";

	public static final String DELETE_PROCESS_HARMONIZATION_DASHBOARD = "delete from rt_app.rt_abm_phd_pub where published_date::date = ?";

	public static final String BEFORE_INSERT_SELECT_PROCESS_HARMONIZATION_DASHBOARD = "select count(*) from rt_app.rt_abm_phd_pub where published_date::date = ? and oppty_number = ?";

	public static final String SELECT_ORDERS_AND_SLOTTING_CARD_DETAIL = "select orders_amount_out , orders_percentage_out , cm_amount_out , cm_percentage_out , nova_lt_out , aerogt_out , v_period_out, cm_as_perc\r\n"
			+ "from rt_app.get_abm_card_details(?,?,?,?,?,?)";

	public static final String SELECT_ORDERS_AND_SLOTTING_BAR_GRAPH = "select * from rt_app.get_order_plotting_graph(?,?,?,?,?,?,?)";

	public static final String SELECT_ORDERS_AND_SLOTTING_COMMIT = "select * from rt_app.get_orders_slot_committable(?,?,?,?,?,?,?,?,?,?,?)";

	public static final String SELECT_ORDERS_AND_SLOTTING_UPSIDE = "select * from rt_app.get_orders_slot_upsidetable(?,?,?,?,?,?,?,?,?,?,?)";

	public static final String SELECT_ORDERS_AND_SLOTTING_OMIT = "";

	public static final String GET_SEGMENT = "select distinct (case when coalesce(rt_segment,'Unassigned') = '' then 'Unassigned' else coalesce(rt_segment,'Not Assigned') end) as segment from rt_app.rt_abm_dm order by 1";

	public static final String GET_KEY_ACCOUNT = "select distinct (case when coalesce(key_account,'Unassigned') = '' then 'Unassigned' else coalesce(key_account,'Not Assigned') end) as key_account from rt_app.rt_abm_dm  order by 1";

	public static final String GET_INSTALLATION_COUNTRY = "select distinct (case when coalesce(install_country,'Unassigned') = '' then 'Unassigned' else coalesce(install_country,'Not Assigned') end) as installation_country from rt_app.rt_abm_dm  order by 1";

	public static final String GET_CTO_VS_ETO = "select  distinct (case when coalesce(ctovseto,'Unassigned') = '' then 'Unassigned' else coalesce(ctovseto,'Not Assigned') end) as ctovseto from rt_app.rt_abm_dm  order by 1";

	public static final String SELECT_ORDERS_AND_SLOTTING_COMMIT_COUNT_AMT = "select coalesce(sum(line_amount_usd),0) as total_amount ,count(oppty_number)  as total_oppty\r\n"
			+ "from rt_app.get_orders_slot_committable(?,?,?,?,?,?,?,?,?,?,?)";

	public static final String SELECT_ORDERS_AND_SLOTTING_UPSIDE_COUNT_AMT = "\r\n"
			+ "select coalesce(sum(line_amount_usd),0) as total_amount ,count(oppty_number)  as total_oppty\r\n"
			+ "from rt_app.get_orders_slot_upsidetable(?,?,?,?,?,?,?,?,?,?,?)";

	public static final String SELECT_ORDERS_AND_SLOTTING_MORE_INFO_ICON = "select * from rt_app.get_orders_slot_moreinfo(?)";

	public static final String INSERT_INTO_ORDERS_AND_SLOTTING_TABLES = "INSERT INTO rt_app.rt_abm_orders_slots_save\r\n"
			+ "(slot_insight, oppty_number, region, segment, oppty_name, oppty_amt_usd, oppty_cm_perc, eod_bc, nova_lt, aerogt, active_slot_type, eod_pc_status, eod_pc, eod_oc, published_by, published_date, eod_period, qmi_category, ep_product, slot_insight_published_tooltip)\r\n"
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String DELETE_FROM_ORDERS_AND_SLOTTING_TABLE = "delete from rt_app.rt_abm_orders_slots_save where published_date::date = ?";

	public static final String SELECT_ORDERS_AND_SLOTTING_TABLE = "select count(*) from rt_app.rt_abm_orders_slots_save where published_date::date = ?";

	public static final String BEFORE_INSERT_SELECT_ORDERS_AND_SLOTTING = "select count(*) from rt_app.rt_abm_orders_slots_save where published_date::date = ? and oppty_number = ?";

	public static final String SELECT_ORDERS_AND_SLOTTING_PIE_CHART = "select commit_at_risk_out , upside_out , active_pipeline_perc_out\r\n"
			+ "from\r\n" + "rt_app.get_order_slotting_piechart(?,?,?,?,?,?,?)";

	public static final String DOWNLOAD_UPLOAD_EXCEL = "select * from rt_app.rt_abm_orders_slots_save oss";

	public static final String SELECT_ORDERS_AND_SLOTTING_CLOSED_WON = "select * from rt_app.get_orders_slot_closedwon(?,?,?,?,?,?,?,?,?,?,?)";

	public static final String GET_LAST_PUBLISHED_DATE_FOR_ORDERS_AND_SLOT = "select (select distinct to_char(max(published_date) ,'DD-MON-YYYY HH24:MI:SS') || ' GMT'  as last_published_Date\r\n"
			+ "from rt_app.rt_abm_orders_slots_save) last_published_Date ,\r\n"
			+ "(select coalesce(concat(au.first_name,' ',au.last_name),'') as last_published_by from\r\n"
			+ "rt_app.rt_abm_orders_slots_save pp join rt_app.rt_adm_user au\r\n"
			+ "on (au.user_sso = pp.published_by and pp.published_date::date = (select max(published_date::date)\r\n"
			+ "from rt_app.rt_abm_orders_slots_save))\r\n"
			+ " group by concat(au.first_name,' ',au.last_name))last_published_by";

	public static final String SELECT_ORDERS_AND_SLOTTING_CLOSED_WON_MORE_INFO = "select * from rt_app.get_closedwon_moreinfo(?)";

	public static final String INSERT_PROCESS_HARMONIZATION_DASHBOARD_TABLE = "INSERT INTO rt_app.rt_abm_phd_save (oppty_number, oppty_name_dm, qmi_category, eod, slot_request_type, business_release, slot_confirmation, slot_start_date, slot_insight_last_updated, slot_insight_last_published, last_saved_by, last_saved_date, f0_color, f0_tooltip, f1_color, f1_tooltip, f2_color, f2_tooltip, f3_color, f3_tooltip, proposed_slot_insight_tooltip, slot_insight_last_saved_color, slot_insight_last_saved_tooltip, slot_insight_to_be_published_tooltip, note, comments, oppty_amount_usd) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String DELETE_PROCESS_HARMONIZATION_DASHBOARD_TABLE = "delete from rt_app.rt_abm_phd_save";

	public static final String DM_UPDATED_ON ="select distinct to_char(max(insert_dt::date),'DD-MON-YYYY') as dm_updated_on from rt_app.rt_abm_dm";
	
	public static final String OBP_UPDATED_ON ="select distinct to_char(max(insert_dt::date),'DD-MON-YYYY') as obp_updated_on from rt_app.rt_abm_obp";
	
	public static final String P6_UPDATED_ON ="select distinct to_char(max(insert_date::date),'DD-MON-YYYY') as p6_updated_on from rt_stage.rt_abm_p6_asi_stage";
	
	public static final String SEAP_UPDATED_ON="select distinct to_char(max(insert_date::date),'DD-MON-YYYY') as seap_updated_on from rt_stage.rt_abm_sc_source";

	public static final String GET_ORDERS_SLOTTING_LAST_PUBLISHED_ON = "select to_char(max(published_date::date),'DD-MON-YYYY') as published_on from rt_app.rt_abm_orders_slots_save;";

	public static final String SELECT_ORDERS_AND_SLOTTING_COMMIT_EXCEL_DATA = "select * from rt_app.get_orders_slot_download(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String DELETE_ORDERS_AND_SLOTTING_DETAILS = "delete from rt_app.rt_abm_orders_slots_save";

	public static final String GET_QMI_CATEGORY = "select coalesce(qmi_category,'Unassigned') Qmi_Category from\r\n"
			+ "(select distinct qmi_category\r\n"
			+ "from  rt_app.rt_abm_asi asi order by 1 asc) a;";

	public static final String GET_SLOT_REQUEST_TYPE = "select distinct coalesce(slot_request_type,'Unassigned')  slot_Request_type\r\n"
			+ "from  rt_app.rt_abm_asi asi order by 1;";

	public static final String GET_BUDGETARY_TYPE = "select distinct coalesce(deal_budgetary_type,'Unassigned') Deal_Budgetory_Type \r\n"
			+ "from  rt_app.rt_abm_asi asi order by 1;";

	public static final String GET_FORECAST_EXCEPTION = "select coalesce(forecast_exception,'Unassigned')  Forecast_Exception from\r\n"
			+ "(select distinct  forecast_exception from rt_app.rt_abm_asi asi order by 1) a ;";

	public static final String GET_ALL_ICONS_COMMENTS = "select * from rt_app.rt_cat_selection_master where attribute_group = 'SLOT_INSIGHT_COMMENTS'";

	public static final String GET_PHD_DOWNLOAD_EXCEL_WITH_TOGGLE = "SELECT\r\n"
			+ "phd.f0_color,\r\n"
			+ "phd.f0_tooltip,\r\n"
			+ "phd.f1_color,\r\n"
			+ "phd.f1_tooltip,\r\n"
			+ "phd.f2_color,\r\n"
			+ "phd.f2_tooltip,\r\n"
			+ "phd.f3_color,\r\n"
			+ "phd.f3_tooltip,\r\n"
			+ "phd.slot_insight_last_updated AS SLOT_INSIGHT_LAST_UPDATED,\r\n"
			+ "phd.slot_insight_last_published AS SLOT_INSIGHT_LAST_PUBLISHED,\r\n"
			+ "phd.slot_insight_last_updated_tooltip as SLOT_INSIGHT_LAST_UPDATED_TOOLTIP,\r\n"
			+ "phd.note as NOTE,\r\n"
			+ "phd.coments as COMENTS,\r\n"
			+ "phd.line_amount_usd as OPPTY_AMOUNT_USD,\r\n"
			+ "asi.oppty_number AS OPPTY_NUMBER,\r\n"
			+ "asi.oppty_name_dm AS OPPTY_NAME,\r\n"
			+ "asi.qmi_category AS QMI_CATEGORY,\r\n"
			+ "asi.slot_request_type AS SLOT_REQUEST_TYPE,\r\n"
			+ "asi.business_release AS BUSINESS_RELEASE,\r\n"
			+ "asi.slot_confirmation AS SLOT_CONFIRMATION,\r\n"
			+ "asi.eod AS EOD,\r\n"
			+ "asi.slot_start_date AS SLOT_START_DATE,\r\n"
			+ "asi.alert AS ALERT,\r\n"
			+ "asi.deal_budgetary_type AS DEAL_BUDGETARY_TYPE,\r\n"
			+ "asi.forecast_exception AS FORECAST_EXCEPTION,\r\n"
			+ "asi.closed AS CLOSED,\r\n"
			+ "asi.record_type_name AS RECORD_TYPE_NAME,\r\n"
			+ "asi.seap_eod_at_submission AS SEAP_EOD_AT_SUBMISSION,\r\n"
			+ "asi.seap_oppty_amount_at_submission AS SEAP_OPPTY_AMOUNT_AT_SUBMISSION,\r\n"
			+ "asi.seap_sos_at_submission AS SEAP_SOS_AT_SUBMISSION,\r\n"
			+ "asi.seap_main_reason AS SEAP_MAIN_REASON,\r\n"
			+ "asi.seap_sop_current_status AS SEAP_SOP_CURRENT_STATUS,\r\n"
			+ "asi.seap_logged_date AS SEAP_LOGGED_DATE,\r\n"
			+ "asi.seap_wf_number AS SEAP_WF_NUMBER\r\n"
			+ "FROM rt_app.get_phd_details() phd JOIN rt_app.rt_abm_asi asi ON (asi.oppty_number=phd.oppty_num)\r\n"
			+ "right join\r\n"
			+ "( select distinct (extract(year from date_q))||'-Q'||(extract(quarter from date_q)) as otd_y_q from\r\n"
			+ "( SELECT d::date as date_q FROM\r\n"
			+ "(select case when ? ='current quarter' then generate_series((now())::date,now()+interval '0 day', '1 day')\r\n"
			+ "when ? ='current year' then generate_series((date_part('year',now()::date)||'-'||01||'-'||01)::date,(date_part('year',now()::date)||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='3years' then generate_series((date_part('year',now())||'-'||01||'-'||01)::date,(date_part('year',now())+2||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='next year' then generate_series((date_part('year',now())+1||'-'||01||'-'||01)::date,(date_part('year',now())+1||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='6q rolling' then generate_series((date_part('year',now()::date)||'-'||01||'-'||01)::date,(date_part('year',now()::date)||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "end) AS gs(d))a) q\r\n"
			+ "on (extract(year from phd.eod_date::date))||'-Q'||(extract(quarter from phd.eod_date::date))=otd_y_q\r\n"
			+ "where (case when ? <> '0' then (phd.qmi_category) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.slot_insight_last_updated ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.slot_request_type ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.deal_budgetary_type) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.forecastexception) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and phd.slot_insight_disconnection = 'Y' ;";

	public static final String GET_PHD_DOWNLOAD_EXCEL_WITHOUT_TOGGLE = "SELECT\r\n"
			+ "phd.f0_color,\r\n"
			+ "phd.f0_tooltip,\r\n"
			+ "phd.f1_color,\r\n"
			+ "phd.f1_tooltip,\r\n"
			+ "phd.f2_color,\r\n"
			+ "phd.f2_tooltip,\r\n"
			+ "phd.f3_color,\r\n"
			+ "phd.f3_tooltip,\r\n"
			+ "phd.slot_insight_last_updated AS SLOT_INSIGHT_LAST_UPDATED,\r\n"
			+ "phd.slot_insight_last_published AS SLOT_INSIGHT_LAST_PUBLISHED,\r\n"
			+ "phd.slot_insight_last_updated_tooltip as SLOT_INSIGHT_LAST_UPDATED_TOOLTIP,\r\n"
			+ "phd.note as NOTE,\r\n"
			+ "phd.coments as COMENTS,\r\n"
			+ "phd.line_amount_usd as OPPTY_AMOUNT_USD,\r\n"
			+ "asi.oppty_number AS OPPTY_NUMBER,\r\n"
			+ "asi.oppty_name_dm AS OPPTY_NAME,\r\n"
			+ "asi.qmi_category AS QMI_CATEGORY,\r\n"
			+ "asi.slot_request_type AS SLOT_REQUEST_TYPE,\r\n"
			+ "asi.business_release AS BUSINESS_RELEASE,\r\n"
			+ "asi.slot_confirmation AS SLOT_CONFIRMATION,\r\n"
			+ "asi.eod AS EOD,\r\n"
			+ "asi.slot_start_date AS SLOT_START_DATE,\r\n"
			+ "asi.alert AS ALERT,\r\n"
			+ "asi.deal_budgetary_type AS DEAL_BUDGETARY_TYPE,\r\n"
			+ "asi.forecast_exception AS FORECAST_EXCEPTION,\r\n"
			+ "asi.closed AS CLOSED,\r\n"
			+ "asi.record_type_name AS RECORD_TYPE_NAME,\r\n"
			+ "asi.seap_eod_at_submission AS SEAP_EOD_AT_SUBMISSION,\r\n"
			+ "asi.seap_oppty_amount_at_submission AS SEAP_OPPTY_AMOUNT_AT_SUBMISSION,\r\n"
			+ "asi.seap_sos_at_submission AS SEAP_SOS_AT_SUBMISSION,\r\n"
			+ "asi.seap_main_reason AS SEAP_MAIN_REASON,\r\n"
			+ "asi.seap_sop_current_status AS SEAP_SOP_CURRENT_STATUS,\r\n"
			+ "asi.seap_logged_date AS SEAP_LOGGED_DATE,\r\n"
			+ "asi.seap_wf_number AS SEAP_WF_NUMBER\r\n"
			+ "FROM rt_app.get_phd_details() phd JOIN rt_app.rt_abm_asi asi ON (asi.oppty_number=phd.oppty_num)\r\n"
			+ "right join\r\n"
			+ "( select distinct (extract(year from date_q))||'-Q'||(extract(quarter from date_q)) as otd_y_q from\r\n"
			+ "( SELECT d::date as date_q FROM\r\n"
			+ "(select case when ? ='current quarter' then generate_series((now())::date,now()+interval '0 day', '1 day')\r\n"
			+ "when ? ='current year' then generate_series((date_part('year',now()::date)||'-'||01||'-'||01)::date,(date_part('year',now()::date)||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='3years' then generate_series((date_part('year',now())||'-'||01||'-'||01)::date,(date_part('year',now())+2||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='next year' then generate_series((date_part('year',now())+1||'-'||01||'-'||01)::date,(date_part('year',now())+1||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "when ? ='6q rolling' then generate_series((date_part('year',now()::date)||'-'||01||'-'||01)::date,(date_part('year',now()::date)||'-'||12||'-'||31)::date, '1 day')\r\n"
			+ "end) AS gs(d))a) q\r\n"
			+ "on (extract(year from phd.eod_date::date))||'-Q'||(extract(quarter from phd.eod_date::date))=otd_y_q\r\n"
			+ "where (case when ? <> '0' then (phd.qmi_category) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.slot_insight_last_updated ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.slot_request_type ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.deal_budgetary_type) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.forecastexception) = any (string_to_array (?,',')) else true end );";

	public static final String GET_PHD_DOWNLOAD_EXCEL_WITH_TOGGLE_OVERALL_HORIZON = "SELECT\r\n"
			+ "phd.f0_color,\r\n"
			+ "phd.f0_tooltip,\r\n"
			+ "phd.f1_color,\r\n"
			+ "phd.f1_tooltip,\r\n"
			+ "phd.f2_color,\r\n"
			+ "phd.f2_tooltip,\r\n"
			+ "phd.f3_color,\r\n"
			+ "phd.f3_tooltip,\r\n"
			+ "phd.slot_insight_last_updated AS SLOT_INSIGHT_LAST_UPDATED,\r\n"
			+ "phd.slot_insight_last_published AS SLOT_INSIGHT_LAST_PUBLISHED,\r\n"
			+ "phd.slot_insight_last_updated_tooltip as SLOT_INSIGHT_LAST_UPDATED_TOOLTIP,\r\n"
			+ "phd.note as NOTE,\r\n"
			+ "phd.coments as COMENTS,\r\n"
			+ "phd.line_amount_usd as OPPTY_AMOUNT_USD,\r\n"
			+ "asi.oppty_number AS OPPTY_NUMBER,\r\n"
			+ "asi.oppty_name_dm AS OPPTY_NAME,\r\n"
			+ "asi.qmi_category AS QMI_CATEGORY,\r\n"
			+ "asi.slot_request_type AS SLOT_REQUEST_TYPE,\r\n"
			+ "asi.business_release AS BUSINESS_RELEASE,\r\n"
			+ "asi.slot_confirmation AS SLOT_CONFIRMATION,\r\n"
			+ "asi.eod AS EOD,\r\n"
			+ "asi.slot_start_date AS SLOT_START_DATE,\r\n"
			+ "asi.alert AS ALERT,\r\n"
			+ "asi.deal_budgetary_type AS DEAL_BUDGETARY_TYPE,\r\n"
			+ "asi.forecast_exception AS FORECAST_EXCEPTION,\r\n"
			+ "asi.closed AS CLOSED,\r\n"
			+ "asi.record_type_name AS RECORD_TYPE_NAME,\r\n"
			+ "asi.seap_eod_at_submission AS SEAP_EOD_AT_SUBMISSION,\r\n"
			+ "asi.seap_oppty_amount_at_submission AS SEAP_OPPTY_AMOUNT_AT_SUBMISSION,\r\n"
			+ "asi.seap_sos_at_submission AS SEAP_SOS_AT_SUBMISSION,\r\n"
			+ "asi.seap_main_reason AS SEAP_MAIN_REASON,\r\n"
			+ "asi.seap_sop_current_status AS SEAP_SOP_CURRENT_STATUS,\r\n"
			+ "asi.seap_logged_date AS SEAP_LOGGED_DATE,\r\n"
			+ "asi.seap_wf_number AS SEAP_WF_NUMBER\r\n"
			+ "FROM rt_app.get_phd_details() phd JOIN rt_app.rt_abm_asi asi ON (asi.oppty_number=phd.oppty_num)\r\n"
			+ "where (case when ? <> '0' then (phd.qmi_category) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.slot_insight_last_updated ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.slot_request_type ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.deal_budgetary_type) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.forecastexception) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and phd.slot_insight_disconnection = 'Y'";

	public static final String GET_PHD_DOWNLOAD_EXCEL_WITHOUT_TOGGLE_OVERALL_HORIZON = "SELECT\r\n"
			+ "phd.f0_color,\r\n"
			+ "phd.f0_tooltip,\r\n"
			+ "phd.f1_color,\r\n"
			+ "phd.f1_tooltip,\r\n"
			+ "phd.f2_color,\r\n"
			+ "phd.f2_tooltip,\r\n"
			+ "phd.f3_color,\r\n"
			+ "phd.f3_tooltip,\r\n"
			+ "phd.slot_insight_last_updated AS SLOT_INSIGHT_LAST_UPDATED,\r\n"
			+ "phd.slot_insight_last_published AS SLOT_INSIGHT_LAST_PUBLISHED,\r\n"
			+ "phd.slot_insight_last_updated_tooltip as SLOT_INSIGHT_LAST_UPDATED_TOOLTIP,\r\n"
			+ "phd.note as NOTE,\r\n"
			+ "phd.coments as COMENTS,\r\n"
			+ "phd.line_amount_usd as OPPTY_AMOUNT_USD,\r\n"
			+ "asi.oppty_number AS OPPTY_NUMBER,\r\n"
			+ "asi.oppty_name_dm AS OPPTY_NAME,\r\n"
			+ "asi.qmi_category AS QMI_CATEGORY,\r\n"
			+ "asi.slot_request_type AS SLOT_REQUEST_TYPE,\r\n"
			+ "asi.business_release AS BUSINESS_RELEASE,\r\n"
			+ "asi.slot_confirmation AS SLOT_CONFIRMATION,\r\n"
			+ "asi.eod AS EOD,\r\n"
			+ "asi.slot_start_date AS SLOT_START_DATE,\r\n"
			+ "asi.alert AS ALERT,\r\n"
			+ "asi.deal_budgetary_type AS DEAL_BUDGETARY_TYPE,\r\n"
			+ "asi.forecast_exception AS FORECAST_EXCEPTION,\r\n"
			+ "asi.closed AS CLOSED,\r\n"
			+ "asi.record_type_name AS RECORD_TYPE_NAME,\r\n"
			+ "asi.seap_eod_at_submission AS SEAP_EOD_AT_SUBMISSION,\r\n"
			+ "asi.seap_oppty_amount_at_submission AS SEAP_OPPTY_AMOUNT_AT_SUBMISSION,\r\n"
			+ "asi.seap_sos_at_submission AS SEAP_SOS_AT_SUBMISSION,\r\n"
			+ "asi.seap_main_reason AS SEAP_MAIN_REASON,\r\n"
			+ "asi.seap_sop_current_status AS SEAP_SOP_CURRENT_STATUS,\r\n"
			+ "asi.seap_logged_date AS SEAP_LOGGED_DATE,\r\n"
			+ "asi.seap_wf_number AS SEAP_WF_NUMBER\r\n"
			+ "FROM rt_app.get_phd_details() phd JOIN rt_app.rt_abm_asi asi ON (asi.oppty_number=phd.oppty_num)\r\n"
			+ "where (case when ? <> '0' then (phd.qmi_category) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.slot_insight_last_updated ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.slot_request_type ) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.deal_budgetary_type) = any (string_to_array (?,',')) else true end )\r\n"
			+ "and (case when ? <> '0' then (phd.forecastexception) = any (string_to_array (?,',')) else true end )";

	public static final String SELECT_PHD_PRESENT_DATA = "select * from rt_app.rt_abm_phd_save";

	public static final String SELECT_PHD_PUBLISH_PRESENT_DATA = "select * from rt_app.rt_abm_phd_pub where published_date::date = ?";

	
}
