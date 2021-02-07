package thymealeaf.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class TestVacation {
    private String title;

    /**
     * カレンダーの開始日付
     */
    private String start;

    /**
     * カレンダーの終了日付
     */
    private String end;
}
