package com.yy.mobile.rollingtextview.strategy

import com.yy.mobile.rollingtextview.PreviousProgress

/**
 * Created by 张宇 on 2018/3/6.
 * E-mail: zhangyu4@yy.com
 * YY: 909017428
 */
class StickyStrategy(val factor: Double) : NormalAnimationStrategy() {

    init {
        if (factor <= 0.0 && factor > 1.0) {
            throw IllegalStateException("factor must be in range (0,1] but now is $factor")
        }
    }

    override fun getFactor(previousProgress: PreviousProgress, index: Int, size: Int, charList: List<Char>): Double {
        return factor
    }
}