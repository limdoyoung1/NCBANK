package ncbank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import ncbank.beans.CodeMoneyBean;

public interface CodeMoneyMapper {

    // code_money table ���� ������ ��������
    @Select("select code_money, code_money_name " +
            "from code_money " + "order by code_money")
    public List<CodeMoneyBean> getCodeMoneyList();

    @Select("select code_money_name " +
            "from code_money " + "where code_money = #{code_money}")
    public String getCodeMoneyName(String code_money);
}
